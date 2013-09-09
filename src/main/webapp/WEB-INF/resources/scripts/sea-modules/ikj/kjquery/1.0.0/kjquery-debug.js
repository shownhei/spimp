define("ikj/kjquery/1.0.0/kjquery-debug", [ "$-debug", "ikj/rest/1.0.0/rest-debug", "gallery/ztree/3.5.2/ztree-debug", "ikj/gritter/1.0.0/gritter-debug", "ikj/bootstrap/2.3.2/bootstrap-debug", "ikj/serialize-object/1.0.0/serialize-object-debug" ], function(require, exports, module) {
    var $ = require("$-debug");
    require("ikj/rest/1.0.0/rest-debug");
    require("gallery/ztree/3.5.2/ztree-debug");
    require("ikj/gritter/1.0.0/gritter-debug");
    require("ikj/bootstrap/2.3.2/bootstrap-debug");
    require("ikj/serialize-object/1.0.0/serialize-object-debug");
    module.exports = $;
});
