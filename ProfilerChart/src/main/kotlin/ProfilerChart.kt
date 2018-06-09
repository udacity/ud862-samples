
import org.knowm.xchart.CategoryChartBuilder
import org.knowm.xchart.SwingWrapper
import java.io.File
import java.text.DecimalFormat

fun processFile(filename:String, name:String) : Triple<String, List<Double>, List<Double>> {
    val file = File(filename)
    val pairs = arrayListOf<Pair<Double,Double>>()

    file.forEachLine { line ->
        if (line.startsWith("HISTOGRAM")) {
            var tempPairs = line.split(" ").drop(1)
            // split into elements and drop "HISTOGRAM:"

            // split again on = and remove the ms suffixes
            tempPairs.forEach {
                val item = it.split("=")
                pairs.add(Pair(item[0].removeSuffix("ms").toDouble(), item[1].toDouble()))
            }
        }
    }

    // remove pairs with 0 frames drawn at that interval
    pairs.removeIf { it.second == 0.0}

    // Total Frames
    val totalFrames = pairs.sumByDouble { it.second }
    println("Total Frames: $totalFrames")

    // Janky Frames (frames that took more than 16 ms to run
    val decimalFormat = DecimalFormat("###.00")
    val jankyFrames = pairs.filter { it.first > 16 }.sumByDouble { it.second }
    println("Janky Frames: $jankyFrames (${decimalFormat.format(jankyFrames/totalFrames *100)}%)")



    return Triple(name, pairs.map { it.first }, pairs.map { it.second.toDouble() })
}

fun main(args: Array<String>) {
    args.forEach {
        println(it)
    }

    val data = processFile(args[0], "DataSet")

    val chart = CategoryChartBuilder()
            .width(800).height(600)
            .title("Histogram of Draw Time Frequencies")
            .xAxisTitle("Number of ms to Draw Frame")
            .yAxisTitle("Number of Frames")
            .build()

    chart.styler.setHasAnnotations(true)

    chart.addSeries(data.first, data.second, data.third)

    SwingWrapper(chart).displayChart()
}