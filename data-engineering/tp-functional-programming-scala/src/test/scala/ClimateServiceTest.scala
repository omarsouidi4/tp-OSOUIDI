import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

class ClimateServiceTest extends AnyFunSuite {
  test("containsWordGlobalWarming - non climate related words should return false") {
    assert(ClimateService.isClimateRelated("pizza") == false)
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("IPCC") == true)
  }

  //Add the new test
  test("isClimateRelated: should return true for a climate related sentence with new keyword") {
    val sentence = "LGreenhouse gas emissions cause global warming.."
    assert(ClimateService.isClimateRelated(sentence) == true)
  }

  //@TODO
  test("parseRawData") {
    val missingValueRecord = (2003, 6, -99.99)
    val list2 = List(missingValueRecord)

    val output2 = List(None)

    assert(ClimateService.parseRawData(list2) == output2)
  }

  test("findMinMaxCO2 - finds correct min and max values") {
    val firstRecord = (2003, 1, 355.2)
    val secondRecord = (2004, 1, 375.2)
    val missingValueRecord = (2003, 6, -99.99)
    val list = List(firstRecord, secondRecord, missingValueRecord)

    val parsedList = ClimateService.parseRawData(list)
    val (min, max) = ClimateService.findMinMaxCO2(parsedList)

    assert(min.contains(355.2))
    assert(max.contains(375.2))
  }
  test("findMinMaxCO2 - handles empty list") {
    val emptyList: List[Option[CO2Record]] = List()
    val (min, max) = ClimateService.findMinMaxCO2(emptyList)

    assert(min.isEmpty)
    assert(max.isEmpty)
  }
  test("findMinMaxCO2ByYear - finds correct min and max values for a specific year") {
    val record1 = Some(CO2Record(2003, 1, 355.2))
    val record2 = Some(CO2Record(2003, 2, 358.9))
    val record3 = Some(CO2Record(2004, 1, 375.2))
    val record4 = Some(CO2Record(2004, 2, 378.4))
    val list = List(record1, record2, record3, record4)

    val (min, max) = ClimateService.findMinMaxCO2ByYear(list, 2003)

    assert(min.contains(355.2))
    assert(max.contains(358.9))
  }

  test("findMinMaxCO2ByYear - handles empty list") {
    val emptyList: List[Option[CO2Record]] = List()
    val (min, max) = ClimateService.findMinMaxCO2ByYear(emptyList, 2003)

    assert(min.isEmpty)
    assert(max.isEmpty)
  }
  test("findDifferenceMinMaxCO2 - finds correct difference between min and max values") {
    val record1 = Some(CO2Record(2003, 1, 355.2))
    val record2 = Some(CO2Record(2003, 2, 358.9))
    val record3 = Some(CO2Record(2004, 1, 375.2))
    val record4 = Some(CO2Record(2004, 2, 378.4))
    val list = List(record1, record2, record3, record4)

    val difference = ClimateService.findDifferenceMinMaxCO2(list)

    assert(difference.contains(23.2))
  }

  test("findDifferenceMinMaxCO2 - handles empty list") {
    val emptyList: List[Option[CO2Record]] = List()
    val difference = ClimateService.findDifferenceMinMaxCO2(emptyList)

    assert(difference.isEmpty)
  }
}





  //@TODO
  //test("filterDecemberData") {
    //assert(true == false)
  //}
//}
