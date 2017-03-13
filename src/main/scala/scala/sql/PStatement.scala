package scala.sql

import java.sql.{Connection, ResultSet, Timestamp}

import org.joda.time.DateTime

/**
  * Created by nuboat on Feb 5, 2017
  */
case class PStatement(sql: String)(implicit conn: Connection) {

  private var counter: Int = 1

  private val pstmt = conn.prepareStatement(sql)

  def setBoolean(param: Boolean): PStatement = setBoolean(counter, param)

  def setBoolean(ind: Int, param: Boolean): PStatement = {
    pstmt.setBoolean(ind, param)
    count
  }

  def setInt(param: Int): PStatement = setInt(counter, param)

  def setInt(ind: Int, param: Int): PStatement = {
    pstmt.setInt(ind, param)
    count
  }

  def setLong(param: Long): PStatement = setLong(counter, param)

  def setLong(ind: Int, param: Long): PStatement = {
    pstmt.setLong(ind, param)
    count
  }

  def setDouble(param: Double): PStatement = setDouble(counter, param)

  def setDouble(ind: Int, param: Double): PStatement = {
    pstmt.setDouble(ind, param)
    count
  }

  def setString(param: String): PStatement = setString(counter, param)

  def setString(ind: Int, param: String): PStatement = {
    pstmt.setString(ind, param)
    count
  }

  def setTimestamp(param: Timestamp): PStatement = setTimestamp(counter, param)

  def setTimestamp(ind: Int, param: Timestamp): PStatement = {
    pstmt.setTimestamp(ind, param)
    count
  }

  def setDateTime(param: DateTime): PStatement = setDateTime(counter, param)

  def setDateTime(ind: Int, param: DateTime): PStatement = {
    if (param == null) {
      pstmt.setTimestamp(ind, null)
    } else {
      pstmt.setTimestamp(ind, new Timestamp(param.getMillis))
    }
    count
  }

  def initial: PStatement = {
    counter = 1
    this
  }

  def update: Int = pstmt.executeUpdate

  def query: ResultSet = pstmt.executeQuery

  def queryOne[A](block: ResultSet => A): Option[A] = {
    val rs = pstmt.executeQuery
    if (rs.next) Some(block(rs)) else None
  }

  def queryList[A](block: ResultSet => A): List[A] = {
    val rs = pstmt.executeQuery
    new Iterator[A] {
      override def hasNext: Boolean = rs.next()

      override def next: A = block(rs)
    }.toList
  }

  def addBatch(): PStatement = {
    pstmt.addBatch()
    counter = 1
    this
  }

  def executeBatch: Array[Int] = pstmt.executeBatch

  def close: PStatement = {
    pstmt.close()
    this
  }

  def cancel: PStatement = {
    pstmt.cancel()
    this
  }

  private def count: PStatement = {
    counter = counter + 1
    this
  }

}