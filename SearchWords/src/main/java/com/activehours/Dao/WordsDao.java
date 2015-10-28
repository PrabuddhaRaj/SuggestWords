package com.activehours.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.activehours.model.Customer;

public class WordsDao implements CustomerDAO {
	public DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insert(Customer customer) {

		String sql = "INSERT INTO WORD " + "(wordno, Lemma, Value) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customer.getCustId());
			ps.setString(2, customer.getName());
			ps.setInt(3, customer.getAge());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public String findWordByValue(String word) {

		String sql = "SELECT SOUNDEX(?) AS VALUE ";

		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, word);
			ResultSet rs = ps.executeQuery();
			String soundexValue = "";
			if (rs.next()) {
				soundexValue = rs.getString("VALUE");
			}
			rs.close();
			ps.close();
			return soundexValue;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public List<String> findSuggestions(String Value) {
		String sql = "SELECT LEMMA FROM WORD WHERE VALUE = ?";

		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Value);
			ResultSet rs = ps.executeQuery();
			List<String> suggestedWords = new ArrayList<String>();
			while (rs.next()) {
				suggestedWords.add(rs.getString("LEMMA"));
			}
			rs.close();
			ps.close();
			return suggestedWords;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}
}