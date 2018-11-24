package servlets.authentification.prevented;

import dao.Factory;
import dao.access.BanDao;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static database.Utils.BLACKLIST;


public final class BlackList {

    private static Object lock = new Object();
    private static volatile boolean mStarted = false;
    private static volatile List<String> mLocalBlacklist;
    private static final Logger log = Logger.getLogger(BlackList.class);


    private static BlackList mInstance = null;

    private BlackList() {
        mLocalBlacklist = new ArrayList<>();
        onStart();
    }

    public static BlackList getInstance() {
        if (mInstance == null) {
            mInstance = new BlackList();
        }
        return mInstance;
    }

    private void onStart() {

        Observable.just("updating blacklist...")
                .subscribeOn(Schedulers.newThread())
                .repeat()
                .subscribe(t -> {
                            onUpdate(((BanDao) Factory.getDao(BLACKLIST)).getInes());
                            log.info("updating blacklist list "+t);
                            Thread.sleep(mStarted ? 900000 : 100); // 15 mins
                        },
                        Throwable::printStackTrace);

    }

    public boolean isBlacklisted(String tmpIne) {
        synchronized (lock) {
            if (mStarted)
                return mLocalBlacklist.contains(tmpIne);
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error(e,e);
            e.printStackTrace();
        }

        return isBlacklisted(tmpIne);
    }

    private void onUpdate(List<String> newList) {
        synchronized (lock) {
            mStarted = true;
            mLocalBlacklist.clear();
            mLocalBlacklist.addAll(newList);
        }
    }

    private void onFinish() {
        log.warn(">> stoping updates of blacklist");
        mInstance = null;
        mLocalBlacklist.clear();
    }
}
