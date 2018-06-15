package in.norbor.yoda.utilities

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, PropertyNamingStrategy}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.util.Try

/**
  * Created by Peerapat A on Mar 18, 2017
  */
object Json {

  private val mapper = new ObjectMapper with ScalaObjectMapper

  mapper.registerModule(JodaJacksonModule)
  mapper.registerModule(DefaultScalaModule)
  mapper.setSerializationInclusion(Include.NON_NULL)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  private val isSnakeCase = Try(Conf.bool("yoda.json.is_snake_case")).getOrElse(true)
  if (isSnakeCase)
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)

  def toJson(obj: AnyRef): String = mapper.writeValueAsString(obj)

  def toObject[T: Manifest](content: String): Option[T] = {
    if (content == null || content.isEmpty)
      None
    else
      Option(mapper.readValue[T](content))
  }

}
