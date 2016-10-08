object Main extends App{
  val metricsData = MetricsService.getMetrics("src/main/scala/TestInheritance.scala")

  MetricsService.saveToFile(metricsData)
}