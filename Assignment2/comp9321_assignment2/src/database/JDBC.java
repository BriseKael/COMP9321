package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bean.*;

/**
 * SQL has 4 operation: select, insert, update, delete which Xxx include
 * tablename and column name: isXxxExist: check Xxx exist, return boolean
 * result; getXxx: return an Xxx Object where Xx = Xx; addXxx: insert a data
 * into a table, some return the increased id; setXxx: update a data in a table;
 * removeXxx: delete a data in a table;
 */

/**
 * Java: Class name is UpperCamelCase, other is lowerCamelCase. SQL: all is
 * lowercase with '_'.
 */

public class JDBC {
    /**
     * @method getConnection()
     * @return Connection
     */
    private static Connection getConnection() {
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/Brise.Zoey";
        String username = "Brise.Zoey";
        String password = "";
        Connection connection = null;
        try {
            Class.forName(driver); // classLoader,加载对应驱动
            connection = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return connection;
    }

    /**
     * ----------------------------------------------------------------------------------------------------
     * operations for create a table, use for only once.
     * 
     * @return True if sun raises from the east
     */
    private static boolean createUser() {
        boolean succeed = true;
        Connection connection = null;
        String sql = "DROP TABLE IF EXISTS public.user;\n" + "CREATE TABLE public.user\n" + "(\n"
                + "    user_id serial NOT NULL,\n" + "    username character varying(16) NOT NULL,\n"
                + "    password character varying(16) NOT NULL,\n" + "    email character varying(64) NOT NULL,\n"
                + "    nickname character varying(128),\n" + "    first_name character varying(64),\n"
                + "    last_name character varying(64),\n" + "    gender character varying(16),\n"
                + "    date_of_birth date,\n" + "    photo text,\n" + "    is_admin boolean NOT NULL,\n"
                + "    is_actived boolean NOT NULL,\n" + "    is_banned boolean NOT NULL,\n"
                + "    PRIMARY KEY (user_id),\n" + "    UNIQUE (username),\n"
                + "    CHECK (email like '%@%') NOT VALID\n" + ")";

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.execute();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            succeed = false;
        }
        return succeed;
    }

    private static boolean createUserRelation() {
        boolean succeed = true;
        Connection connection = null;
        String sql = "DROP TABLE IF EXISTS public.user_relation;\n" + "CREATE TABLE public.user_relation\n" + "(\n"
                + "    subjective_user_id integer NOT NULL,\n" + "    objective_user_id integer NOT NULL,\n"
                + "    relation_type character varying(16) NOT NULL,\n"
                + "    relation_time timestamp(0) without time zone NOT NULL,\n"
                + "    PRIMARY KEY (subjective_user_id, objective_user_id),\n"
                + "    CHECK (subjective_user_id <> objective_user_id) NOT VALID\n" + ")";

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.execute();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            succeed = false;
        }
        return succeed;
    }

    private static boolean createPost() {
        boolean succeed = true;
        Connection connection = null;
        String sql = "DROP TABLE IF EXISTS public.post;\n" + "CREATE TABLE public.post\n" + "(\n"
                + "    post_id serial NOT NULL,\n" + "    author_id integer NOT NULL,\n"
                + "    content text NOT NULL,\n" + "    post_time timestamp(0) without time zone NOT NULL,\n"
                + "    PRIMARY KEY (post_id)\n" + ")";

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.execute();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            succeed = false;
        }
        return succeed;
    }

    private static boolean createPostUserRelation() {
        boolean succeed = true;
        Connection connection = null;
        String sql = "DROP TABLE IF EXISTS public.post_user_relation;\n" + "CREATE TABLE public.post_user_relation\n"
                + "(\n" + "    subjective_post_id integer NOT NULL,\n" + "    objective_user_id integer NOT NULL,\n"
                + "    relation_type character varying(16) NOT NULL,\n"
                + "    relation_time timestamp(0) without time zone NOT NULL,\n"
                + "    PRIMARY KEY (subjective_post_id, objective_user_id)\n" + ")";

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.execute();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            succeed = false;
        }
        return succeed;
    }

    private static boolean createNotification() {
        boolean succeed = true;
        Connection connection = null;
        String sql = "DROP TABLE IF EXISTS public.notification;\n" + "CREATE TABLE public.notification\n" + "(\n"
                + "    notification_id serial NOT NULL,\n" + "    subjective_user_id integer NOT NULL,\n"
                + "    objective_user_id integer NOT NULL,\n"
                + "    notification_type character varying(16) NOT NULL,\n"
                + "    notification_time timestamp(0) without time zone NOT NULL,\n"
                + "    PRIMARY KEY (notification_id),\n"
                + "    CHECK (subjective_user_id <> objective_user_id) NOT VALID\n" + ")";

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.execute();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            succeed = false;
        }
        return succeed;
    }

    private static boolean createTables() {
        boolean succeed = true;

        succeed &= createUser();
        succeed &= createUserRelation();
        succeed &= createPost();
        succeed &= createPostUserRelation();
        succeed &= createNotification();

        return succeed;
    }

    /**
     * ----------------------------------------------------------------------------------------------------
     * operations for User table. bean like UserBean
     */

    /**
     * @param userId
     *            user.user_id, pk
     * @return true if this id is exist; false if not exist, or DB is fucked
     */
    public static boolean isUserExist(int userId) {
        int successNumber = 0;

        Connection connection = null;
        String sql = "SELECT count(*)\n" + "FROM public.\"user\"\n" + "WHERE user_id = ?";

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                successNumber += rs.getInt(1);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (successNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param username
     *            user.username, unique username
     * @return false if this username is not exist; true if exist, or DB is fucked
     * @SQL select count(*) from user where username = ?;
     */
    public static boolean isUsernameExist(String username) {
        int successNumber = 0;

        Connection connection = null;
        String sql = "SELECT count(*)\n" + "FROM public.\"user\"\n" + "WHERE username = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                successNumber += rs.getInt(1);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (successNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param userId
     *            user.user_id, pk
     * @return return UserBean, null for a null UserBean
     */
    public static UserBean getUser(int userId) {
        UserBean userBean = new UserBean();
        Connection connection = null;
        String sql = "SELECT user_id, username, password, email, nickname, first_name, last_name, gender, date_of_birth, photo, is_admin, is_actived, is_banned\n"
                + "FROM public.\"user\"\n" + "WHERE user_id = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                userBean.setUserId(rs.getInt(1));
                userBean.setUsername(rs.getString(2));
                userBean.setPassword(rs.getString(3));
                userBean.setEmail(rs.getString(4));
                userBean.setNickname(rs.getString(5));
                userBean.setFirstName(rs.getString(6));
                userBean.setLastName(rs.getString(7));
                userBean.setGender(rs.getString(8));
                userBean.setDateOfBirth(rs.getDate(9));
                userBean.setPhoto(rs.getString(10));
                userBean.setAdmin(rs.getBoolean(11));
                userBean.setActived(rs.getBoolean(12));
                userBean.setBanned(rs.getBoolean(13));
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return userBean;
    }

    /**
     * @param nickname
     *            user.nickname
     * @return List<UserBean> that user.nickname like %user.nickname%; ignore the
     *         upper and lower case; empty list if no match result;
     */
    public static List<UserBean> getUsersByNickName(String nickname) {
        List<UserBean> userBeans = new ArrayList<>();
        Connection connection = null;
        String sql = "SELECT user_id, username, password, email, nickname, first_name, last_name, gender, date_of_birth, photo, is_admin, is_actived, is_banned\n"
                + "FROM public.\"user\"\n" + "WHERE lower(nickname) LIKE ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setString(1, "%" + nickname.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UserBean userBean = new UserBean();

                userBean.setUserId(rs.getInt(1));
                userBean.setUsername(rs.getString(2));
                userBean.setPassword(rs.getString(3));
                userBean.setEmail(rs.getString(4));
                userBean.setNickname(rs.getString(5));
                userBean.setFirstName(rs.getString(6));
                userBean.setLastName(rs.getString(7));
                userBean.setGender(rs.getString(8));
                userBean.setDateOfBirth(rs.getDate(9));
                userBean.setPhoto(rs.getString(10));
                userBean.setAdmin(rs.getBoolean(11));
                userBean.setActived(rs.getBoolean(12));
                userBean.setBanned(rs.getBoolean(13));

                userBeans.add(userBean);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return userBeans;
    }

    /**
     * @param user
     *            an UserBean object, with id null or 0.
     * @return user_id that in the database. 0 for false.
     */
    public static int addUser(String username, String password, String email, String nickname, String firstName,
            String lastName, String gender, Date dateOfBirth, String photo, Boolean isAdmin, Boolean isActived,
            boolean isBanned) {
        int userId = 0;
        Connection connection = null;
        String sql = "INSERT INTO public.\"user\"(\n"
                + "    username, password, email, nickname, first_name, last_name, gender, date_of_birth, photo, is_admin, is_actived, is_banned)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + "    RETURNING user_id";

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, nickname);
            pstmt.setString(5, firstName);
            pstmt.setString(6, lastName);
            pstmt.setString(7, gender);
            pstmt.setDate(8, dateOfBirth);
            pstmt.setString(9, photo);
            pstmt.setBoolean(10, isAdmin);
            pstmt.setBoolean(11, isActived);
            pstmt.setBoolean(12, isBanned);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                userId = rs.getInt(1);
            }

            connection.commit();
            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return userId;
    }

    /**
     * @param userBean
     *            an UserBean, with userId as null
     * @return user_id that in the database. 0 for false.
     */
    public static int addUser(UserBean userBean) {
        int userId = 0;
        Connection connection = null;
        String sql = "INSERT INTO public.\"user\"(\n"
                + "    username, password, email, nickname, first_name, last_name, gender, date_of_birth, photo, role, is_banned)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + "    RETURNING user_id";

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setString(1, userBean.getUsername());
            pstmt.setString(2, userBean.getPassword());
            pstmt.setString(3, userBean.getEmail());
            pstmt.setString(4, userBean.getNickname());
            pstmt.setString(5, userBean.getFirstName());
            pstmt.setString(6, userBean.getLastName());
            pstmt.setString(7, userBean.getGender());
            pstmt.setDate(8, userBean.getDateOfBirth());
            pstmt.setString(9, userBean.getPhoto());
            pstmt.setBoolean(10, userBean.isAdmin());
            pstmt.setBoolean(11, userBean.isActived());
            pstmt.setBoolean(12, userBean.isBanned());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                userId = rs.getInt(1);
            }

            connection.commit();
            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return userId;
    }

    /**
     * ----------------------------------------------------------------------------------------------------
     * operations for user_relation table, relations for bean UserBeans
     */

    /**
     * @param subjectiveUserId
     *            user_relation.subjective_user_id, pk1
     * @param objective_user_id
     *            User_relation.objective_user_id, pk2
     * @return false if this data is not exist; true if exist, or DB is fucked
     */
    public static boolean isUserRelationExist(int subjectiveUserId, int objectiveUserId) {
        int successNumber = 0;

        Connection connection = null;
        String sql = "SELECT count(*)\n" + "FROM public.\"user_relation\"\n" + "WHERE subjective_user_id = ?\n"
                + "AND objective_user_id = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, subjectiveUserId);
            pstmt.setInt(2, objectiveUserId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                successNumber += rs.getInt(1);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (successNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param subjectiveUserId
     *            user_relation.subjective_user_id, pk1
     * @param objectiveUserId
     *            user_relation.objective_user_id, pk2
     * @return String relationType (FRIEND) or "UNKNOW"
     */
    public static String getUserRelationType(int subjectiveUserId, int objectiveUserId) {
        String relationType = "UNKNOW";
        Connection connection = null;
        String sql = "SELECT relation_type\n" + "FROM public.user_relation\n" + "WHERE subjective_user_id = ?\n"
                + "AND objective_user_id = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, subjectiveUserId);
            pstmt.setInt(2, objectiveUserId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                relationType = rs.getString(1);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return relationType;
    }

    /**
     * @param subjectiveUserId
     *            user_relation.subjective_user_id, pk1
     * @param objectiveUserId
     *            user_relation.objective_user_id, pk2
     * @return Timestamp relationTime for this relation created, null for not exist.
     */
    public static Timestamp getUserRelationTime(int subjectiveUserId, int objectiveUserId) {
        Timestamp relationTime = null;
        Connection connection = null;
        String sql = "SELECT relation_time\n" + "FROM public.user_relation\n" + "WHERE subjective_user_id = ?\n"
                + "AND objective_user_id = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, subjectiveUserId);
            pstmt.setInt(2, objectiveUserId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                relationTime = rs.getTimestamp(1);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return relationTime;
    }

    /**
     * @param subjectiveUserId
     *            user_relation.subjective_user_id, pk1
     * @param objectiveUserId
     *            user_relation.objective_user_id, pk2
     * @param relationType
     *            user_relation.relation_type
     * @return true if succeed, false if failed.
     */
    private static boolean addUserRelation(int subjectiveUserId, int objectiveUserId, String relationType) {
        int successNumber = 0;

        Connection connection = null;
        String sql = "INSERT INTO public.user_relation(\n"
                + "subjective_user_id, objective_user_id, relation_type, relation_time)\n"
                + "VALUES (?, ?, ?, CURRENT_TIMESTAMP(0))";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, subjectiveUserId);
            pstmt.setInt(2, objectiveUserId);
            pstmt.setString(3, relationType);

            successNumber += pstmt.executeUpdate();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (successNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param subjectiveUserId
     *            user_relation.subjective_user_id, pk1
     * @param objectiveUserId
     *            user_relation.objective_user_id, pk2
     * @param relationType
     *            user_relation.relation_type
     * @return 1 if update FRIEND relation succeed, 0 if not
     */
    private static boolean setRelationType(int subjectiveUserId, int objectiveUserId, String relationType) {
        int successNumber = 0;

        Connection connection = null;
        String sql = "UPDATE public.user_relation\n" + "    SET relation_type=?, relation_time=CURRENT_TIMESTAMP(0)\n"
                + "    WHERE subjective_user_id = ?\n" + "    AND objective_user_id = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setString(1, relationType);
            pstmt.setInt(2, subjectiveUserId);
            pstmt.setInt(3, objectiveUserId);

            successNumber += pstmt.executeUpdate();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (successNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ----------------------------------------------------------------------------------------------------
     * operations for post table. bean like PostBean, LikedPostBean
     */

    /**
     * @param post_id
     *            post.post_id, pk
     * @return a PostBean if exist, null PostBean if not
     */
    public static PostBean getPost(int postId) {
        PostBean postBean = new PostBean();

        Connection connection = null;
        String sql = "SELECT post_id, author_id, content, post_time\n" + "    FROM public.post\n"
                + "    WHERE post_id = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                postBean.setPostId(rs.getInt(1));
                postBean.setAuthorId(rs.getInt(2));
                postBean.setContent(rs.getString(3));
                postBean.setPostTime(rs.getTimestamp(4));
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return postBean;
    }

    /**
     * @param author_id
     *            post.author_id
     * @return a list of PostBeans by this author id, empty list if not exist.
     */
    public static List<PostBean> getPostsByAuthorId(int authorId) {
        List<PostBean> postBeans = new ArrayList<>();

        Connection connection = null;
        String sql = "SELECT post_id, author_id, content, post_time\n" + "    FROM public.post\n"
                + "    WHERE author_id = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setInt(1, authorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PostBean postBean = new PostBean();

                postBean.setPostId(rs.getInt(1));
                postBean.setAuthorId(rs.getInt(2));
                postBean.setContent(rs.getString(3));
                postBean.setPostTime(rs.getTimestamp(4));

                postBeans.add(postBean);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return postBeans;
    }

    /**
     * @param userId
     *            user_relation.subjective_user_id, pk1
     * @return a list of posts by this user's friend, order by time desc
     */
    public static List<PostBean> getFriendPostsByUserId(int userId) {
        List<PostBean> postBeans = new ArrayList<>();
        Connection connection = null;
        String sql = "SELECT post_id, author_id, content, post_time\n" + "    FROM post, user_relation, public.user\n"
                + "    WHERE subjective_user_id = ?\n" + "    AND objective_user_id = author_id\n"
                + "    AND objective_user_id = user_id\n" + "    AND is_banned = false\n"
                + "    ORDER BY post_time DESC";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PostBean postBean = new PostBean();

                postBean.setPostId(rs.getInt(1));
                postBean.setAuthorId(rs.getInt(2));
                postBean.setContent(rs.getString(3));
                postBean.setPostTime(rs.getTimestamp(4));

                postBeans.add(postBean);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return postBeans;
    }

    /**
     * @param authorId
     *            post.author_id
     * @param content
     *            post.content
     * @return true if add post succeed, false if not
     */
    public static boolean addPost(int authorId, String content) {
        int successNumber = 0;

        Connection connection = null;
        String sql = "INSERT INTO public.post(\n" + "    author_id, content, post_time)\n"
                + "    VALUES (?, ?, CURRENT_TIMESTAMP(0))";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, authorId);
            pstmt.setString(2, content);

            successNumber += pstmt.executeUpdate();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (successNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param postBean
     *            a PostBean object with postId and postTime as null
     * @return true if add succeed, false for not.
     */
    public static boolean addPost(PostBean postBean) {
        int successNumber = 0;

        Connection connection = null;
        String sql = "INSERT INTO public.post(\n" + "    author_id, content, post_time)\n"
                + "    VALUES (?, ?, CURRENT_TIMESTAMP(0))";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, postBean.getAuthorId());
            pstmt.setString(2, postBean.getContent());

            successNumber += pstmt.executeUpdate();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (successNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    // removePost(int post_id) delete this post

    /**
     * ----------------------------------------------------------------------------------------------------
     * operations for post_user_relation table, beans like PostBean and UserBean
     */

    /**
     * @param subjectivePostId
     *            post_user_relation.subjective_post_id, pk1
     * @param objectiveUserId
     *            post_user_relation.objective_user_id, pk2
     * @return true if relation exist. false if not
     */
    public static boolean isPostUserRelationExist(int subjectivePostId, int objectiveUserId) {
        int successNumber = 0;

        Connection connection = null;
        String sql = "SELECT count(*)\n" + "FROM public.\"post_user_relation\"\n" + "WHERE subjective_post_id = ?\n"
                + "AND objective_user_id = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, subjectivePostId);
            pstmt.setInt(2, objectiveUserId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                successNumber += rs.getInt(1);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (successNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param subjectivePostId
     *            post_user_relation.subjective_post_id, pk1
     * @param objectiveUserId
     *            post_user_relation.objective_user_id, pk2
     * @return String relationType (LIKED) or "UNKNOW"
     */
    public static String getPostUserRelationType(int subjectivePostId, int objectiveUserId) {
        String relationType = "UNKNOW";
        Connection connection = null;
        String sql = "SELECT relationType\n" + "FROM public.post_user_relation\n" + "WHERE subjective_post_id = ?\n"
                + "AND objective_user_id = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, subjectivePostId);
            pstmt.setInt(2, objectiveUserId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                relationType = rs.getString(1);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return relationType;
    }

    /**
     * ----------------------------------------------------------------------------------------------------
     * operations for notification table, bean like NotificationBean
     */

    /**
     * @param subjectiveUserId
     *            notification.subjective_user_id
     * @return a list of notificationBeans by this user, empty list if no
     *         notification
     */
    public static List<NotificationBean> getNotificationsBySubjectiveUserId(int subjectiveUserId) {
        List<NotificationBean> notificationBeans = new ArrayList<>();

        Connection connection = null;
        String sql = "SELECT notification_id, subjective_user_id, objective_user_id, notification_type, notification_time\n"
                + "    FROM public.notification\n" + "    WHERE subjective_user_id = ?";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setInt(1, subjectiveUserId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                NotificationBean notificationBean = new NotificationBean();

                notificationBean.setNotificationId(rs.getInt(1));
                notificationBean.setSubjectiveUserId(rs.getInt(2));
                notificationBean.setObjectiveUserId(rs.getInt(3));
                notificationBean.setNotificationType(rs.getString(4));
                notificationBean.setNotificationTime(rs.getTimestamp(5));

                notificationBeans.add(notificationBean);
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return notificationBeans;
    }

    /**
     * @param subjectiveUserId
     *            notification.subjective_user_id, self user id.
     * @param objectiveUserId
     *            notification.objective_user_id, target user id.
     * @param notificationType
     *            type
     * @return true if add succeed, false for not.
     */
    public static boolean addNotification(int subjectiveUserId, int objectiveUserId, String notificationType) {
        int successNumber = 0;

        Connection connection = null;
        String sql = "INSERT INTO public.notification(\n"
                + "    subjective_user_id, objective_user_id, notification_type, notification_time)\n"
                + "    VALUES (?, ?, ?, CURRENT_TIMESTAMP(0))";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, subjectiveUserId);
            pstmt.setInt(2, objectiveUserId);
            pstmt.setString(3, notificationType);

            successNumber += pstmt.executeUpdate();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (successNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param notificationBean
     *            a NotificationBean with notificationId and notificationTime as
     *            null
     * @return true if add succeed, false for not.
     */
    public static boolean addNotification(NotificationBean notificationBean) {
        int successNumber = 0;

        Connection connection = null;
        String sql = "INSERT INTO public.notification(\n"
                + "    subjective_user_id, objective_user_id, notification_type, notification_time)\n"
                + "    VALUES (?, ?, ?, CURRENT_TIMESTAMP(0))";
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);

            pstmt.setInt(1, notificationBean.getSubjectiveUserId());
            pstmt.setInt(2, notificationBean.getObjectiveUserId());
            pstmt.setString(3, notificationBean.getNotificationType());

            successNumber += pstmt.executeUpdate();

            connection.commit();

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (successNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ----------------------------------------------------------------------------------------------------
     * Self Operation:
     */

    /**
     * Let user2 be a friend of user1 with the primary_user_id and secondary_user_id
     * if not friend yet, insert it; else, update it. you need call this function
     * 
     * @param subjectiveUserId
     *            user_relation.subjective_user_id, pk1
     * @param objectiveUserId
     *            user_relation.objective_user_id, pk2
     * @return 2 if make A and B both friends, 1 for half succeed, 0 for not succeed
     */
    public static boolean makeUsersAsFriend(int subjectiveUserId, int objectiveUserId) {
        boolean succeed = true;
        if (isUserExist(subjectiveUserId) && isUserExist(objectiveUserId)) {
            if (isUserRelationExist(subjectiveUserId, objectiveUserId)) {
                succeed &= setRelationType(subjectiveUserId, objectiveUserId, "FRIEND");
            } else {
                succeed &= addUserRelation(subjectiveUserId, objectiveUserId, "FRIEND");
            }

            if (isUserRelationExist(objectiveUserId, subjectiveUserId)) {
                succeed &= setRelationType(objectiveUserId, subjectiveUserId, "FRIEND");
            } else {
                succeed &= addUserRelation(objectiveUserId, subjectiveUserId, "FRIEND");
            }
        } else {
            succeed = false;
        }
        return succeed;
    }

    /**
     * @param number
     *            number of random user to generate
     * @return list of succeed inserted userIds, a list of integer.
     */
    public static List<Integer> generateRandomUsers(int number) {

        List<Integer> userIds = new ArrayList<>();
        int i = 0;
        while (i < number) {
            String randomName = "TestUser" + (int) (Math.random() * 100000000);
            if (isUsernameExist(randomName)) {
                continue;
            }
            long millis = System.currentTimeMillis();
            Date randomDate = new Date(millis);

            userIds.add(addUser(randomName, "" + (int) (Math.random() * 100000000), "brise.kael@gmail.com", randomName,
                    "Test", randomName.substring(4, randomName.length()), "BOY", randomDate, "", false, true, false));

            i += 1;
        }
        return userIds;
    }

    public static void main(String args[]) {

    }
}
