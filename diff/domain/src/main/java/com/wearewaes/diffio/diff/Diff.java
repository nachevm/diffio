package com.wearewaes.diffio.diff;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(chain = true)
@Entity
@Table(name = "diff")
class Diff {

  @Id
  @Column(nullable = false)
  private Long id;
  @Setter
  private String leftData;
  @Setter
  private String rightData;

  boolean isLeftAndRightDataDifferentLength() {
    if (leftData == null && rightData == null) {
      return false;
    }
    return leftData == null || rightData == null || leftData.length() != rightData.length();
  }

  @Override
  public boolean equals(Object o) {
    return this == o || (o instanceof Diff && id != null && id.equals(((Diff) o).getId()));
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
