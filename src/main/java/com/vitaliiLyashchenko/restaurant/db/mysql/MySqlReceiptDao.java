package com.vitaliiLyashchenko.restaurant.db.mysql;

import com.vitaliiLyashchenko.restaurant.db.ConnectionPool;
import com.vitaliiLyashchenko.restaurant.db.ReceiptDao;
import com.vitaliiLyashchenko.restaurant.db.entity.Receipt;
import com.vitaliiLyashchenko.restaurant.exceptions.DbException;
import com.vitaliiLyashchenko.restaurant.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlReceiptDao implements ReceiptDao {

    private static Receipt mapReceipt(ResultSet rs) throws SQLException {
        return new Receipt.Builder()
                .setId(rs.getLong("id"))
                .setUserId(rs.getLong("user_id"))
                .setStatusId(rs.getLong("status_id"))
                .setTotal(rs.getInt("total"))
                .setManagerId(rs.getLong("manager_id"))
                .setCreateDate(rs.getTimestamp("create_date"))
                .getReceipt();
    }

    @Override
    public List<Receipt> getUserReceipts(long userId) throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_USER_RECEIPTS)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt receipt = mapReceipt(rs);
                    receipt.setDishes(getReceiptDishes(receipt.getId()));
                    receipts.add(receipt);
                }
            }
            return receipts;
        } catch (SQLException e) {
            throw new DbException("Cannot getUserReceipts", e);
        }
    }

    private List<Receipt.Dish> getReceiptDishes(long receiptId) throws SQLException {
        List<Receipt.Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_RECEIPT_DISHES)) {
            ps.setLong(1, receiptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt.Dish dish = new Receipt.Dish.Builder()
                            .setId(rs.getLong("id"))
                            .setName(rs.getString("name"))
                            .setPrice(rs.getInt("price"))
                            .setCount(rs.getInt("count"))
                            .getDish();
                    dishes.add(dish);
                }
            }
        }
        return dishes;
    }

    @Override
    public List<Receipt> getAllReceipts() throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_ALL_RECEIPTS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Receipt receipt = mapReceipt(rs);
                receipt.setDishes(getReceiptDishes(receipt.getId()));
                receipts.add(receipt);
            }
            return receipts;
        } catch (SQLException e) {
            throw new DbException("Cannot getAllReceipts", e);
        }
    }

    @Override
    public List<Receipt> getAllReceiptsAcceptedBy(long managerId) throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_RECEIPTS_APPROVED_BY)) {
            ps.setLong(1, managerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt receipt = mapReceipt(rs);
                    receipt.setDishes(getReceiptDishes(receipt.getId()));
                    receipts.add(receipt);
                }
            }
            return receipts;
        } catch (SQLException e) {
            throw new DbException("Cannot getAllReceiptsAcceptedBy", e);
        }
    }

    @Override
    public List<Receipt> getNotApproved() throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_NOT_APPROVED);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Receipt receipt = mapReceipt(rs);
                receipt.setDishes(getReceiptDishes(receipt.getId()));
                receipts.add(receipt);
            }
            return receipts;
        } catch (SQLException e) {
            throw new DbException("Cannot getNotApproved", e);
        }
    }

    @Override
    public void changeStatus(long receiptId, long statusId, long managerId) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.CHANGE_RECEIPT_STATUS)) {
            int k = 0;
            ps.setLong(++k, statusId);
            ps.setLong(++k, managerId);
            ps.setLong(++k, receiptId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Change status failed, no rows attached");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot changeStatus", e);
        }
    }
}
