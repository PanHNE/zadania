package models

import play.api.libs.json.Json

/**
 * Klasa przypadku do reprezentowania Node
 * @param name   nazwa
 * @param id     identyfikator
 * @param nodes  podlista obieków typu Node
 */
case class Node(
  name: String,
  id: Int,
  var nodes: List[Node] = Nil
)

object Node {
  implicit val writer = Json.writes[Node]
}