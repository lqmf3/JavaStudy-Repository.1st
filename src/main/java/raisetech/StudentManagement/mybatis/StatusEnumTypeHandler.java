package raisetech.StudentManagement.mybatis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import raisetech.StudentManagement.data.StudentCoursesStatus;

public class StatusEnumTypeHandler extends BaseTypeHandler<StudentCoursesStatus.Status> {

  // 文字列をデータベースに挿入するために列挙型を変換
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, StudentCoursesStatus.Status parameter, JdbcType jdbcType) throws SQLException {
    System.out.println("Status: " + parameter.getValue());  // ここで出力される値を確認


    ps.setString(i, parameter.getValue());  // 文字列に変換
  }

  // 結果セットから列挙型を取得する
  @Override
  public StudentCoursesStatus.Status getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String value = rs.getString(columnName);
    return value == null ? null : StudentCoursesStatus.Status.fromString(value);  // 文字列を列挙型に変換
  }

  // 結果セットから列挙型を取得する（列インデックス使用）
  @Override
  public StudentCoursesStatus.Status getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String value = rs.getString(columnIndex);
    return value == null ? null : StudentCoursesStatus.Status.fromString(value); // 文字列を列挙型に変換
  }

  // CallableStatementから列挙型を取得する
  @Override
  public StudentCoursesStatus.Status getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
    String value = cs.getString(columnIndex);
    return value == null ? null : StudentCoursesStatus.Status.fromString(value);  // 文字列を列挙型に変換
  }
}