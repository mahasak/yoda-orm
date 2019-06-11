package in.norbor.yoda.generator

import in.norbor.yoda.definitions.YodaType.SchemaType

/**
  * @author Peerapat A on April 18, 2018
  */
private[generator] case class ColumnMeta(valName: String
                      , schemaType: SchemaType
                      , _schemaName: String) {

  val schemaName: String = ColumnParser.namingStategy(_schemaName)

}
