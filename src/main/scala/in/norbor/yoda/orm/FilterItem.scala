package in.norbor.yoda.orm

/**
  * @author Peerapat A on Jun 24, 2018
  */
case class FilterItem(name: String
                      , operator: Option[String] = None
                      , value: String)
