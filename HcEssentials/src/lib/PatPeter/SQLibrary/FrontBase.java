package lib.PatPeter.SQLibrary;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.Delegates.HostnameDatabase;
import lib.PatPeter.SQLibrary.Factory.DatabaseFactory;

/**
 * Child class for the FrontBase database.<br>
 * Date Created: 2012-12-18 06:15.
 * 
 * @author Nicholas Solin, a.k.a. PatPeter
 */
public class FrontBase extends Database {
	public enum Statements implements StatementEnum {
	}

	private HostnameDatabase delegate = DatabaseFactory.hostname();

	public FrontBase(Logger log, String prefix, String database) {
		super(log, prefix, "[FrontBase] ");
		setHostname("localhost");
		setPort(20020);
		setDatabase(database);
		setUsername("");
		setPassword("");
		this.driver = DBMS.FrontBase;
	}

	public FrontBase(Logger log, String prefix, String hostname, int port,
			String database, String username, String password) {
		super(log, prefix, "[FrontBase] ");
		setHostname(hostname);
		setPort(port);
		setDatabase(database);
		setUsername(username);
		setPassword(password);
		this.driver = DBMS.FrontBase;
	}

	public FrontBase(Logger log, String prefix, String database, String username) {
		super(log, prefix, "[FrontBase] ");
		setHostname("localhost");
		setPort(20020);
		setDatabase(database);
		setUsername(username);
		setPassword("");
		this.driver = DBMS.FrontBase;
	}

	public FrontBase(Logger log, String prefix, String database,
			String username, String password) {
		super(log, prefix, "[FrontBase] ");
		setHostname("localhost");
		setPort(20020);
		setDatabase(database);
		setUsername(username);
		setPassword(password);
		this.driver = DBMS.FrontBase;
	}

	public String getDatabase() {
		return delegate.getDatabase();
	}

	public String getHostname() {
		return delegate.getHostname();
	}

	private String getPassword() {
		return delegate.getPassword();
	}

	public int getPort() {
		return delegate.getPort();
	}

	@Override
	public StatementEnum getStatement(String query) throws SQLException {
		String[] statement = query.trim().split(" ", 2);
		try {
			Statements converted = Statements.valueOf(statement[0]
					.toUpperCase());
			return converted;
		} catch (IllegalArgumentException e) {
			throw new SQLException("Unknown statement: \"" + statement[0]
					+ "\".");
		}
	}

	public String getUsername() {
		return delegate.getUsername();
	}

	@Override
	protected boolean initialize() {
		try {
			Class.forName("om.frontbase.jdbc.FBJDriver");
			return true;
		} catch (ClassNotFoundException e) {
			this.writeError("FrontBase driver class missing: " + e.getMessage()
					+ ".", true);
			return false;
		}
	}

	@Override
	public boolean isTable(String table) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean open() {
		if (initialize()) {
			String url = "jdbc:FrontBase://" + getHostname() + ":" + getPort()
					+ "/" + getDatabase() + ";";
			try {
				this.connection = DriverManager.getConnection(url,
						getUsername(), getPassword());
				return true;
			} catch (SQLException e) {
				this.writeError(
						"Could not establish a FrontBase connection, SQLException: "
								+ e.getMessage(), true);
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	protected void queryValidation(StatementEnum statement) throws SQLException {
	}

	private void setDatabase(String database) {
		delegate.setDatabase(database);
	}

	private void setHostname(String hostname) {
		delegate.setHostname(hostname);
	}

	private void setPassword(String password) {
		delegate.setPassword(password);
	}

	private void setPort(int port) {
		delegate.setPort(port);
	}

	private void setUsername(String username) {
		delegate.setUsername(username);
	}

	@Override
	public boolean truncate(String table) {
		throw new UnsupportedOperationException();
	}
}
