package controllers

import java.io.File

import javax.inject._
import play.api.mvc._
import models.Node
import org.apache.poi.ss.usermodel.{DataFormatter, Sheet, WorkbookFactory}
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext

@Singleton
class TaskController @Inject()(cc: ControllerComponents)
                              (implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {

  /**
   * Pierwszy widok z pierwszym zadaniem
   */
  def goToFirstTask = Action { implicit request =>
      Ok(views.html.task_1(Json.toJson(taskToShow)))
  }

  /**
   * Drugi widok z drugim zadaniem
   */
  def goToSecondTask = Action { implicit request =>
    Ok(views.html.task_2(taskToShow))
  }

  /**
   * Funkcja to przekazywanie listy typu Node do widoków
   */
  def taskToShow = {
    val sheet = takeFileAndGiveSheet("TEST_SCALA.xlsx")
    val listTuple = addElementToTupleList(sheet)
    val listNodes: List[Node] = tuple4ToTreeNode(listTuple)
    listNodes
  }

  /**
   * Przypisanie elementów z pierwszego poziomu do listy,
   * w trakcie funkcji jest wywoływana funckja dołączjąca elementy 2 poziomu do list Node pierwszego poziomu
   * @param elements lista typu tuple4
   * @return lista typu Node
   */
  def tuple4ToTreeNode(elements : List[(String, String, String, String)]) = {
    var listNodes: List[Node] = List()

    elements.foreach {
      case element@( _, "", "", _) => {
        val firstLevel = new Node(element._1, element._4.toInt)
        listNodes = firstLevel :: listNodes
        firstLevel.nodes = tuple4ToTreeNodeSecondLevel(elements, firstLevel)
      }

      case _ =>
    }

    listNodes.reverse

  }

  /**
   * Przypisanie elementów z drugiego poziomu, poszczególnym elementom z którymi jest wywołana funkcja,
   * w trakcie funkcji jest wywoływana funckja dołączjąca elementy 3 poziomu elementom pierwszego poziomu
   * @param elements lista typu tuple4
   * @return lista typu Node
   */
  def tuple4ToTreeNodeSecondLevel(elements : List[(String, String, String, String)], firstLevelNode : Node) = {
    var listNodesSecondLevel: List[Node] = List()
    elements.foreach {
      case element@( "", _, "", _) if element._2.startsWith(firstLevelNode.name) => {
        val secondLevel = new Node(element._2, element._4.toInt)
        listNodesSecondLevel = secondLevel :: listNodesSecondLevel
        secondLevel.nodes = tuple4ToTreeNodeThirdLevel(elements, secondLevel)
      }

      case _ =>
    }

    listNodesSecondLevel
  }

  /**
   * Przypisanie elementów z trzeciego poziomu, poszczególnym elementom z którymi jest wywołana funkcja
   * @param elements lista typu tuple4
   * @return lista typu Node
   */
  def tuple4ToTreeNodeThirdLevel(elements : List[(String, String, String, String)], secondLevelNode : Node) = {
    var listNodesThirdLevel: List[Node] = List()
    elements.foreach {
      case element@( "", "", _, _) if element._3.startsWith(secondLevelNode.name) => {
        val thirdLevel = new Node(element._3, element._4.toInt)
        listNodesThirdLevel = thirdLevel :: listNodesThirdLevel
      }

      case _ =>
    }

    listNodesThirdLevel
  }

  /**
   * Pobiera path do pliku i generuje sheet
   * @param path ścieżka do pliku
   */
  def takeFileAndGiveSheet(path: String) : Sheet = {
    val fis = new File(path)
    val workbook = WorkbookFactory.create(fis)
    workbook.getSheetAt(0)
  }

  /**
   * Funkcja do zamiany odczytanych elementów z pliku do listu typu tuple4 bez pierwszej lini
   * @param sheet dane wczytane z pliku typu xlsx
   * @return lista typu Tuple[string, string, string, string]
   */
  def addElementToTupleList(sheet: Sheet) = {
    val formatter = new DataFormatter()
    var listTuple: List[(String, String, String, String)] = List()

    sheet.forEach( row => {
      val firstLevel = formatter.formatCellValue(row.getCell(0))
      val secondLevel =  formatter.formatCellValue(row.getCell(1))
      val thirdLevel =  formatter.formatCellValue(row.getCell(2))
      val idLevel =  formatter.formatCellValue(row.getCell(3))

      val element = Tuple4(firstLevel, secondLevel, thirdLevel, idLevel)

      listTuple = element +: listTuple
    })
    listTuple.reverse.drop(1)
  }

}
