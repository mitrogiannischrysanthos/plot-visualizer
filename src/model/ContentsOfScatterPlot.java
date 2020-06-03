package model;

public class ContentsOfScatterPlot {
	
	public ContentsOfScatterPlot() {}
	
	public String setScatterPlotAxis(String xAxisName, String yAxisName, String title) {
		return "<!DOCTYPE html>\r\n" + 
				"<meta charset=\"utf-8\">\r\n" + 
				"\r\n" + 
				"<!-- Load d3.js -->\r\n" + 
				"<script src=\"https://d3js.org/d3.v4.js\"></script>\r\n" + 
				"\r\n" + 
				"<!-- Create a div where the graph will take place -->\r\n" + 
				"<div id=\"my_dataviz\"></div>\r\n" + 
				"\r\n" + 
				"<script>\r\n" + 
				"\r\n" + 
				"// set the dimensions and margins of the graph\r\n" + 
				"var margin = {top: 21, right: 30, bottom: 40, left: 100},\r\n" + 
				"    width = 800 - margin.left - margin.right,\r\n" + 
				"    height = 550 - margin.top - margin.bottom;\r\n" + 
				"    // append the svg object to the body of the page\r\n" + 
				"    var svg = d3.select(\"#my_dataviz\")\r\n" + 
				"      .append(\"svg\")\r\n" + 
				"        .attr(\"width\", width + margin.left + margin.right)\r\n" + 
				"        .attr(\"height\", height + margin.top + margin.bottom)\r\n" + 
				"      .append(\"g\")\r\n" + 
				"        .attr(\"transform\",\r\n" + 
				"              \"translate(\" + margin.left + \",\" + margin.top + \")\")\r\n" + 
				"\r\n" + 
				"//Read the data\r\n" + 
				"d3.csv(\"scatterPlot.csv\", function(data) {\r\n" + 
				"\r\n" + 
				"  // Add X axis\r\n" + 
				"  var x = d3.scaleLinear()\r\n" + 
				"    .domain([0, 0])\r\n" + 
				"    .range([ 0, width ]);\r\n" + 
				"  svg.append(\"g\")\r\n" + 
				"    .attr(\"class\", \"myXaxis\")   // Note that here we give a class to the X axis, to be able to call it later and modify it\r\n" + 
				"    .attr(\"transform\", \"translate(0,\" + height + \")\")\r\n" + 
				"    .call(d3.axisBottom(x))\r\n" + 
				"    .attr(\"opacity\", \"0\")\r\n" + 
				"	\r\n" + 
				"	// Add X axis label:\r\n" + 
				"	svg.append(\"text\")\r\n" + 
				"    .attr(\"text-anchor\", \"end\")\r\n" + 
				"    .attr(\"x\", width)\r\n" + 
				"    .attr(\"y\", height + margin.top + 19)\r\n" + 
				"    .text(\"" + xAxisName + "\");\r\n" + 
				"	\r\n" + 
				"	// Y axis label:\r\n" + 
				"	svg.append(\"text\")\r\n" + 
				"    .attr(\"text-anchor\", \"end\")\r\n" + 
				"    .attr(\"transform\", \"rotate(-90)\")\r\n" + 
				"    .attr(\"y\", -margin.left+20)\r\n" + 
				"    .attr(\"x\", -margin.top)\r\n" + 
				"    .text(\"" + yAxisName + "\")\r\n" + 
				"	\r\n" + 
				"\r\n" + 
				"  // Add Y axis\r\n" + 
				"  var y = d3.scaleLinear()\r\n" + 
				"    //.domain([0, 101])\r\n" + 
				"	.domain([0, d3.max(data, function(d) { return + d.MetricYAxis; })+5])\r\n" + 
				"    .range([ height, 0]);\r\n" + 
				"  svg.append(\"g\")\r\n" + 
				"    .call(d3.axisLeft(y));\r\n" + 
				"\r\n" + 
				"	// Add a tooltip div. Here I define the general feature of the tooltip: stuff that do not depend on the data point.\r\n" + 
				"  // Its opacity is set to 0: we don't see it by default.\r\n" + 
				"  var tooltip = d3.select(\"#my_dataviz\")\r\n" + 
				"    .append(\"div\")\r\n" + 
				"    .style(\"opacity\", 0)\r\n" + 
				"    .attr(\"class\", \"tooltip\")\r\n" + 
				"    .style(\"background-color\", \"white\")\r\n" + 
				"    .style(\"border\", \"solid\")\r\n" + 
				"    .style(\"border-width\", \"1px\")\r\n" + 
				"    .style(\"border-radius\", \"5px\")\r\n" + 
				"    .style(\"padding\", \"10px\")\r\n" + 
				"\r\n" + 
				"	// A function that change this tooltip when the user hover a point.\r\n" + 
				"  // Its opacity is set to 1: we can now see it. Plus it set the text and position of tooltip depending on the datapoint (d)\r\n" + 
				"  var mouseover = function(d) {\r\n" + 
				"    tooltip\r\n" + 
				"      .style(\"opacity\", 1)\r\n" + 
				"  }\r\n" + 
				"\r\n" + 
				"  var mousemove = function(d) {\r\n" + 
				"    tooltip\r\n" + 
				"      .html(\"X-Axis: \" + d.MetricXAxis + \"&nbsp &nbsp &nbsp\" + \"Y-Axis: \" + d.MetricYAxis + \"&nbsp &nbsp &nbsp\" + \"Year: \" + d.Year)\r\n" + 
				"      .style(\"left\", (d3.mouse(this)[0]+90) + \"px\") // It is important to put the +90: other wise the tooltip is exactly where the point is an it creates a weird effect\r\n" + 
				"      .style(\"top\", (d3.mouse(this)[1]) + \"px\")\r\n" + 
				"  }\r\n" + 
				"\r\n" + 
				"  // A function that change this tooltip when the leaves a point: just need to set opacity to 0 again\r\n" + 
				"  var mouseleave = function(d) {\r\n" + 
				"    tooltip\r\n" + 
				"      .transition()\r\n" + 
				"      .duration(200)\r\n" + 
				"      .style(\"opacity\", 0)\r\n" + 
				"  }\r\n" + 
				"\r\n" + 
				"  // Add dots\r\n" + 
				"  svg.append('g')\r\n" + 
				"    .selectAll(\"dot\")\r\n" + 
				"    .data(data)\r\n" + 
				"    .enter()\r\n" + 
				"    .append(\"circle\")\r\n" + 
				"      .attr(\"cx\", function (d) { return x(d.MetricXAxis); } )\r\n" + 
				"      .attr(\"cy\", function (d) { return y(d.MetricYAxis); } )\r\n" + 
				"      .attr(\"r\", 1.5)\r\n" + 
				"      .style(\"fill\", \"#69b3a2\")\r\n" + 
				"      .style(\"stroke\", \"white\")\r\n" + 
				"    .on(\"mouseover\", mouseover )\r\n" + 
				"    .on(\"mousemove\", mousemove )\r\n" + 
				"    .on(\"mouseleave\", mouseleave )\r\n" + 
				"	  \r\n" + 
				"	  svg.append(\"text\")\r\n" + 
				"        .attr(\"x\", (width / 2))             \r\n" + 
				"        .attr(\"y\", 0 - (margin.top / 2))\r\n" + 
				"        .attr(\"text-anchor\", \"middle\")  \r\n" + 
				"        .style(\"font-size\", \"16px\") \r\n" + 
				"        .style(\"text-decoration\", \"underline\")  \r\n" + 
				"        .text(\"" + title + "\");\r\n" + 
				"    \r\n" + 
				"    \r\n" + 
				"  // new X axis\r\n" + 
				"  x.domain([0, d3.max(data, function(d) { return +d.MetricXAxis; })]);\r\n" + 
				"  //x.domain([0, 101])\r\n" + 
				"  svg.select(\".myXaxis\")\r\n" + 
				"    .transition()\r\n" + 
				"    .duration(2000)\r\n" + 
				"    .attr(\"opacity\", \"1\")\r\n" + 
				"    .call(d3.axisBottom(x));\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"  svg.selectAll(\"circle\")\r\n" + 
				"    .transition()\r\n" + 
				"    .delay(function(d,i){return(i*3)})\r\n" + 
				"    .duration(2000)\r\n" + 
				"    .attr(\"cx\", function (d) { return x(d.MetricXAxis); } )\r\n" + 
				"    .attr(\"cy\", function (d) { return y(d.MetricYAxis); } )\r\n" + 
				"	.attr(\"r\", 5)\r\n" + 
				"})\r\n" + 
				"\r\n" + 
				"</script>";
	}
}
