package models

import play.api.libs.json.Json

case class Node(
  name: String,
  id: Int,
  var nodes: List[Node] = Nil
)

object Node {
  implicit val writer = Json.writes[Node]
}