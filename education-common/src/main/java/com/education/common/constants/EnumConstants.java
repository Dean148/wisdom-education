package com.education.common.constants;/** * @author zengjintao * @create 2019/3/27 16:09 * @since 1.0 **/public final class EnumConstants {    public enum CorrectType {        SYSTEM(1, "系统判分"), TEACHER(2, "教师判分"), SYSTEM_AND_TEACHER(3, "系统加教师判分");        private int value;        private String name;        CorrectType(int value, String name) {            this.value = value;            this.name = name;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum MessageType {        STUDENT_LOGIN(1, "账号下线通知"), EXAM_CORRECT(2, "试卷批改通知");        private int value;        private String name;        MessageType(int value, String name) {            this.value = value;            this.name = name;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum QuestionCategory {        COURSE(1, "课程试题"), TEST_PAPER(1, "试卷试题");        private int value;        private String name;        QuestionCategory(int value, String name) {            this.value = value;            this.name = name;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum DictType {        TYPE(1, "字典类型"), VALUE(2, "字典类型值");        private int value;        private String name;        DictType(int value, String name) {            this.value = value;            this.name = name;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    /**     * 是或者否     */    public enum Flag {        YES(1, true), NO(0, false);        private int value;        private boolean flag;        Flag(int value, boolean flag) {            this.value = value;            this.flag = flag;        }        public int getValue() {            return value;        }        public boolean isFlag() {            return flag;        }    }    public enum TestPaperType {        GRADE_TEST_PAPER(1, "年级试卷"),        INTELLECT_TEST_PAPER(2, "智能组卷"),        CORRECT_TEST_PAPER(3, "错题纠正试卷");        private int value;        private String name;        TestPaperType(int value, String name) {            this.value = value;            this.name = name;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum AccountType {        ADMIN(1, "公司账号"), SCHOOL_TYPE(2, "学校账号");        private int value;        private String name;        AccountType(int value, String name) {            this.value = value;            this.name = name;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum CorrectStatus {        ERROR(0, "错误"), RIGHT(1, "正确"), CORRECT_RUNNING(2, "待批改"), CORRECTED(3, "已批改");        private int value;        private String name;        CorrectStatus(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    /**     * 考试类型     */    public enum ExamType {        ERROR_EXAM(1, "错题纠正考试"), GRADE_EXAM(2, "年级考试"), ENTRANCE_EXAM(3, "入学考试");        private int value;        private String name;        ExamType(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    /**     * 学校类型     */    public enum SchoolType {        PRIMARY_SCHOOL(1, "小学"), MIDDLE_SCHOOL(2, "初中"), HIGH_SCHOOL(3, "高中");        private int value;        private String name;        SchoolType(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum PlatformType {        WEB_ADMIN(1, "后台管理系统"), WEB_FRONT(2, "前端");        private int value;        private String name;        PlatformType(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum CreateType {        SYSTEM_CREATE(1, "系统创建"), ADMIN_CREATE(2, "管理员创建");        private int value;        private String name;        CreateType(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum Sex {        MAN(1, "男"), WOMAN(2, "女");        private int value;        private String name;        Sex(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    /**     * 充值类型     */    public enum RechargeCardType {        ACCOUNT(1, "账号充值"), SUBJECT(2, "科目充值");        private int value;        private String name;        RechargeCardType(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum ClassQuestionType {        BEFORE_CLASS(1, "课前试题"), AFTER_CLASS(2, "课后试题"), OTHER(0, "其它");        private int value;        private String name;        ClassQuestionType(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    enum SystemType {        FRONT, ADMIN    }    public enum QuestionType {        SINGLE_QUESTION(1, "单选题"), MULTIPLE_QUESTION(2, "多选题"),        FILL_QUESTION(3, "填空题"), SYNTHESIS_QUESTION(4, "综合题"),        INDEFINITE_ITEM_QUESTION(5, "不定项题"),        CALCULATION_QUESTION(7, "计算题"),        JUDGMENT_QUESTION(6, "判断题");        private int value;        private String name;        QuestionType(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }        public static String getName(Integer type) {            QuestionType questionTypes[] = values();            for (QuestionType questionType : questionTypes) {                System.out.println(questionType.getValue());                if (questionType.getValue() == type) {                    return questionType.getName();                }            }            return null;        }    }    /**     * 登录状态     */    public enum LoginStatus {        LOGIN(1, "登录"), LOGIN_OUT(2, "退出");        private int value;        private String name;        LoginStatus(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum CommitAfterType {        SHOW_MARK_AFTER_CORRECT(1, "批改后显示成绩"),        SHOW_MARK_NOW(2, "提交立即显示成绩");        private int value;        private String name;        CommitAfterType(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }    public enum CourseStatus {        DRAUGHT(1, "草稿"), UNDERCARRIAGE(1, "下架"), GROUNDING(2, "上架");        private int value;        private String name;        CourseStatus(int value, String name) {            this.name = name;            this.value = value;        }        public int getValue() {            return value;        }        public String getName() {            return name;        }    }}