ORMLiteStudy
====
Android��ORMLite���g���Ă݂������B

ORMLite��Android�Ŏg����OR�}�b�p�[�B

- ORMLite
  - http://ormlite.com/
  - ���C�Z���X��MIT

![img01.png](img01.png)

�v�_����
----
- Test�N���X(Test.java)�̓}�b�s���O����N���X�B
  - �A�m�e�[�V�������g���āA�e�[�u���Ƃ̃}�b�s���O���`����
- TestDBHelper�N���X((TestDBHelper.java)��DB�𑀍삷�邽�߂̃w���p�[�N���X
  - OrmLiteSqliteOpenHelper�N���X���p�����č쐬����
- ���s�菇�̏ڍׂ�MainActivity.java���Q��

Dao

    public class TestDBHelper extends OrmLiteSqliteOpenHelper  {
         .
         .
         .
        public Dao<Test, Integer> getTestDao() {
        if (simpleDao == null) {
            try {
                simpleDao = getDao(Test.class);
            }
            catch (SQLException e) {
                Log.e(TAG, "getDao() failed...", e);
            }
        }
        return simpleDao;
    }
    
         .
         .
         .
    
    TestDBHelper helper = new TestDBHelper(getBaseContext());
    Dao<Test, Integer> dao = helper.getTestDao();
    

INSERT

    Test ent = new Test("���O", "���[���A�h���X");
    dao.create(ent);

SELECT

    �S���擾
    
        List<Test> results = dao.queryForAll();
    
    1������
    
        Test result = dao.queryBuilder()
                        .where()
                        .eq(Test.COLUMN_ID, 123)
                        .queryForFirst();
    
    �����Ƀ}�b�`���郌�R�[�h�����擾
    
         QueryBuilder<Test, Integer> builder = dao.queryBuilder();
         builder.where().like(Test.COLUMN_EMAIL, "LIKE�̏���");
         builder.offset(0L).limit(100L); // �y�[�W���O
         List<Test> results = builder.query();
    
COUNT

    // queryBuilder.setCountOf(true), dao.countOf()���g�p����
    QueryBuilder queryBuilder = dao.queryBuilder();
    queryBuilder.setCountOf(true);
    // queryBuilder�Ȃ̂ŏ�����where()�ȂǂŐݒ�\
    
    long count = dao.countOf(queryBuilder.prepare());

UPDATE

    Test obj = dao.queryBuilder()
            .where()
            .eq(Test.COLUMN_ID, 123)
            .queryForFirst();
    
    obj.setEmail("new-email@example.com");
    
    dao.createOrUpdate(obj);


DELETE

    �����Ƀ}�b�`�������R�[�h�����폜
    
        DeleteBuilder<Test, Integer> builder = dao.deleteBuilder();
        builder.where().eq(Test.COLUMN_ID, 1234);
        builder.delete();
    
    �S���폜
    
        dao.deleteBuilder().delete();
    

Copyright and license
----
Copyright (c) 2017 yoggy

Released under the [MIT license](LICENSE.txt)
