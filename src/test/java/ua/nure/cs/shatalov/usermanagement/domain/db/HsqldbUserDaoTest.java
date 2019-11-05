package ua.nure.cs.shatalov.usermanagement.domain.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import junit.framework.TestCase;
import ua.nure.cs.shatalov.usermanagement.domain.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {

	private static final String UPDATED_NAME = "Michael";
	private static final String SURNAME = "Doe";
	private static final String NAME = "John";
	private static final long ID = 1L;
	private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:hsqldb:file:db/usermanagement";
    private static final String DRIVER = "org.hsqldb.jdbcDriver";
	
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		// connectionFactory = new ConnectionFactoryImpl();
		dao = new HsqldbUserDao(connectionFactory);

	}

	public void testCreate() {
		// fail("Not yet implemented");
		try {
			User user = new User();
			user.setFirstName(NAME);
			user.setLastName(SURNAME);
			user.setDateOfBirth(new Date());
			assertNull(user.getId());
			user = dao.create(user);
			assertNotNull(user);
			assertNotNull(user.getId());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	public void testFindAll() {
		try {
			Collection collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size", 2, collection.size());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	public void testFind() throws DatabaseException {
        long testUserId = ID + 2;
        User expectedUser = dao.create(createUserWithID(testUserId));
        User actualUser = dao.find(testUserId);
        assertNotNull(actualUser);
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
    }
	
	public void testUpdate() throws DatabaseException {
        User testUser = new User(ID, NAME, SURNAME, new Date());
        dao.create(testUser);

        testUser.setFirstName(UPDATED_NAME);

        dao.update(testUser);
        User updatedUser = dao.find(testUser.getId());
        assertNotNull(updatedUser);
        assertEquals(testUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(testUser.getLastName(), updatedUser.getLastName());
    }

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		// TODO Auto-generated method stub
		connectionFactory = new ConnectionFactoryImpl(DRIVER, URL, USER, PASSWORD);
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		// TODO Auto-generated method stub
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

	private static User createUserWithID(long id) {
		User user = new User(id, NAME, SURNAME, new Date());
        return user;
    }
}
