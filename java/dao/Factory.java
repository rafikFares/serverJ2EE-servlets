package dao;

import dao.access.*;
import dao.universities.UniversityDao;

import static database.Utils.*;

public final class Factory {

    public static DAO getDao(int type) {

        switch (type){
            case USER :
                return new UserDao();

            case UNIVERSITY:
                return new UniversityDao();

            case POSTS :
                return new PostDao();

            case FILE :
                return new FileDao();

            case INTEREST :
                return new InterestDao();

            case COMMENT:
                return new CommentDao();

            case BLACKLIST:
                return new BanDao();

        }

        return null;
    }


}
