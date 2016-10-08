
var nodes = {};

// Compute the distinct nodes from the links.
links.forEach(function(link) {
  link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});
  link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});
});

var width = 960,
    height = 500;

var force = d3.layout.force()
    .nodes(d3.values(nodes))
    .links(links)
    .size([width, height])
    .linkDistance(60)
    .charge(-300)
    .on("tick", tick)
    .start();

var svg = d3.select("body").append("svg")
    .attr("width", width)
    .attr("height", height);

// Per-type markers, as they don't inherit styles.
svg.append("defs").selectAll("marker")
    .data(["suit", "licensing", "resolved"])
  .enter().append("marker")
    .attr("id", function(d) { return d; })
    .attr("viewBox", "0 -5 10 10")
    .attr("refX", 9)
    .attr("refY", 0)
    .attr("markerWidth", 6)
    .attr("markerHeight", 6)
    .attr("orient", "auto")
  .append("path")
    .attr("d", "M0,-5L10,0L0,5");

var path = svg.append("g").selectAll("path")
    .data(force.links())
  .enter().append("path")
    .attr("class", function(d) { return "link " + d.type; })
    .attr("marker-end", function(d) { return "url(#" + d.type + ")"; });

var circle = svg.append("g").selectAll("circle")
    .data(force.nodes())
    .enter().append("circle")
    .style("fill", function(d) {
        return d3.rgb(nodeData[d.name].color);
    })
    .attr("r", function(d){
      var radius = nodeData[d.name].numberOfMethods + nodeData[d.name].numberOfVals + nodeData[d.name].numberOfVars
      if (radius == 0){
        d.radius = 6
        return 6
      } else {
        d.radius = (6 + radius*3)
        return (6 + radius*3)
      }
    })
    .call(force.drag);

var sth = svg.selectAll("circle").on("dblclick", function(d, i) {
    $('.modal-title').html(
        `<div>${d.name}</div>`
    )
    $('#myModal .modal-body').html(
        `<div>Type of object:${nodeData[d.name].typeOfObject}</div>
         <div>Number of Methods:${nodeData[d.name].numberOfMethods}</div>
         <div>Number of vars:${nodeData[d.name].numberOfVars}</div>
         <div>Number of vals:${nodeData[d.name].numberOfVals}</div>`
        );
    $('#myModal').modal()

});


var text = svg.append("g").selectAll("text")
    .data(force.nodes())
  .enter().append("text")
    .attr("x", 8)
    .attr("y", ".31em")
    .text(function(d) { return d.name; });

// Use elliptical arc path segments to doubly-encode directionality.
function tick(e) {
  path.attr("d", function(d){return linkArc(d)});
  circle.attr("transform", transform);
  text.attr("transform", transform);
}

function linkArc(d) {
    if(d.target!=null){
      var dx = d.target.x - d.source.x,
          dy = d.target.y - d.source.y,
          dr = Math.sqrt(dx * dx + dy * dy);

          offsetX = (dx * d.target.radius) / dr;
          offsetY = (dy * d.target.radius) / dr;
      return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + (d.target.x-offsetX) + "," + (d.target.y-offsetY);
    }
}

function transform(d) {
  return "translate(" + d.x + "," + d.y + ")";
}

