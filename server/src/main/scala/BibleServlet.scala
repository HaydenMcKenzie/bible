import org.scalatra._
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec
import com.amazonaws.services.dynamodbv2.model.{GetItemRequest, GetItemResult}

class BibleServlet extends ScalatraServlet {

  val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient()
  val dynamoDB: DynamoDB = new DynamoDB(client)
  val tableName = "YourTableName"

  get("/bible/:book/:chapter") {
    val book = params("book")
    val chapter = params("chapter")

    val table = dynamoDB.getTable(tableName)
    val spec = new GetItemSpec().withPrimaryKey("bibleverse", s"bible#$book#$chapter")
    val item = table.getItem(spec)

    if (item != null) {
      item.toJSON
    } else {
      "Item not found"
    }
  }
}