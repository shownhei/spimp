define(function(require, exports, module) {
  var $ = require('kjquery'),
    svg = require('svg');

  (function() {
    var containerWidth = $('#svgContainer').width(),
      containerHeight = document.documentElement.clientHeight - $('#svgContainer').offset().top - 16;

    svg({
      svg: resources + '/svg/mine.svg',
      wrapperId: 'layer1',
      containerId: 'svgContainer',
      autoFit: false,
      width: containerWidth,
      height: containerHeight,
      needZoomLimit: true,
      needZoom: true,
      needMove: true
    }, function(paper) {
      window.paper = this;
    });
  }());

});
