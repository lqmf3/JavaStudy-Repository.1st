package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.Param;
import raisetech.StudentManagement.data.StudentSearchCriteria;

public class StudentSqlProvider {

  public String buildSearchQuery(@Param("criteria") StudentSearchCriteria criteria) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT * FROM students WHERE is_deleted = 0"); // is_deleted = 0 を追加

    // ここで他の条件を追加する
    if (criteria.getName() != null) {
      sql.append(" AND name = #{criteria.name}");
    }
    if (criteria.getAgeFrom() != null && criteria.getAgeTo() != null) {
      sql.append(" AND age BETWEEN #{criteria.ageFrom} AND #{criteria.ageTo}");
    }
    if (criteria.getRegion() != null) {
      sql.append(" AND region = #{criteria.region}");
    }
    if (criteria.getGender() != null) {
      sql.append(" AND gender = #{criteria.gender}");
    }
    // 他の条件を追加することができます

    return sql.toString();
  }
}
