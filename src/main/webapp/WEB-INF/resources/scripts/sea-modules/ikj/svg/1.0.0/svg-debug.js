define("ikj/svg/1.0.0/svg-debug", [ "./paper-debug", "arale/events/1.1.0/events-debug", "./element-debug", "arale/class/1.0.0/class-debug", "./libs/control-debug", "./move-debug", "./zoom-debug", "./drag-debug", "./slider-debug", "$-debug", "gallery/handlebars/1.0.0/handlebars-debug", "./slider-debug.tpl", "./animate-debug", "arale/easing/1.0.0/easing-debug", "./libs/layer-debug", "./libs/pic-debug", "./libs/fan-debug", "./libs/symbol-debug", "./libs/sensor-debug", "./libs/reader-debug" ], function(require, exports, module) {
    var Paper = require("./paper-debug"), move = require("./move-debug"), zoom = require("./zoom-debug"), drag = require("./drag-debug"), slider = require("./slider-debug"), animate = require("./animate-debug");
    var svg = function(options, callback) {
        if (typeof options == "function") {
            callback = options;
            options = {};
        }
        new Paper(options, function(paper) {
            if (!paper) {
                callback.call(paper, null);
                return;
            }
            //添加rect供IE中拖拽使用
            addDragLayer(paper, options.backgroundColor);
            if (options.needSlider) {
                paper.slider = slider(paper, options);
            }
            if (options.needZoom) {
                paper.zoom = zoom(paper);
                paper.zoom.start();
            }
            if (options.needMove) {
                paper.move = move(paper);
                paper.move.start();
            }
            if (options.needDrag) {
                paper.drag = drag(paper);
            }
            paper.animate = animate(paper);
            callback.call(paper, paper);
        });
    };
    function addDragLayer(paper, color) {
        color = color || "#fff";
        var width = paper.attr(paper.root, "width"), height = paper.attr(paper.root, "height");
        paper.rect(0, 0, width, height).attr({
            fill: color
        }).prependTo();
    }
    Paper.prototype.libs = {
        layer: require("./libs/layer-debug"),
        pic: require("./libs/pic-debug"),
        fan: require("./libs/fan-debug"),
        symbol: require("./libs/symbol-debug"),
        sensor: require("./libs/sensor-debug"),
        reader: require("./libs/reader-debug")
    };
    module.exports = svg;
});

define("ikj/svg/1.0.0/paper-debug", [ "arale/events/1.1.0/events-debug", "ikj/svg/1.0.0/element-debug", "arale/class/1.0.0/class-debug", "ikj/svg/1.0.0/libs/control-debug" ], function(require, exports, module) {
    var Events = require("arale/events/1.1.0/events-debug"), Element = require("ikj/svg/1.0.0/element-debug"), Control = require("ikj/svg/1.0.0/libs/control-debug");
    var NS = {
        xlink: "http://www.w3.org/1999/xlink"
    };
    var _id = 0;
    function nextId() {
        return "KJSVG" + _id++;
    }
    var isSVG = !!(window.SVGAngle || document.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#BasicStructure", "1.1"));
    var rPreBlank = /^\s+/;
    var posFactors = {
        center: [ .5, .5 ],
        top: [ .5, 0 ],
        rightTop: [ 1, 0 ],
        right: [ 1, .5 ],
        rightBottom: [ 1, 1 ],
        bottom: [ .5, 1 ],
        leftBottom: [ 0, 1 ],
        left: [ 0, .5 ],
        leftTop: [ 0, 0 ]
    };
    function Paper(options, callback) {
        this.version = "1.0.0";
        this.isSVG = isSVG;
        this.isChrome = navigator.userAgent.indexOf("Chrome") > 0;
        this.needZoomLimit = options.needZoomLimit;
        this.maxZoom = options.maxZoom || 3;
        this.minZoom = options.minZoom || .6;
        this.wrapperId = options.wrapperId;
        var containerId = options.containerId;
        var container;
        if (containerId) {
            container = document.getElementById(containerId);
        } else {
            container = document.createElement("div");
            container.style.margin = "5px";
            document.body.appendChild(container);
        }
        //检测是否安装插件
        if (!isSVG) {
            try {
                new ActiveXObject("Adobe.SVGCtl");
            } catch (e) {
                callback.call(this, null);
                return;
            }
        }
        var self = this;
        if (options.autoFit === false) {
            this.width = options.width;
            this.height = options.height;
        } else {
            this.width = document.documentElement.clientWidth - 5;
            this.height = document.documentElement.clientHeight - 15;
            window.onresize = function() {
                var width = document.documentElement.clientWidth - 5, height = document.documentElement.clientHeight - 15;
                self.embed.width = width;
                self.embed.height = height;
                self.width = width;
                self.height = height;
                self.trigger("resize");
            };
        }
        var embed = document.createElement("embed");
        embed.src = options.svg || "black.svg";
        embed.width = this.width;
        embed.height = this.height;
        embed.setAttribute("wmode", "transparent");
        container.appendChild(embed);
        this.container = container;
        this.embed = embed;
        function load() {
            var doc;
            try {
                doc = embed.getSVGDocument();
            } catch (e) {}
            if (!doc) {
                //如果时间间隔太小,ASV下会无法正常绘制图形
                setTimeout(load, 200);
            } else {
                self.doc = doc;
                getRoot(doc, callback);
            }
        }
        load();
        function getRoot(doc, callback) {
            var root = doc.documentElement;
            if (!root) {
                setTimeout(function() {
                    getRoot(doc, callback);
                }, 10);
            } else {
                self.root = root;
                if (self.isChrome && options.needMove) {
                    //避免Chrome中整体拖动时文字被选中
                    self.style(self.doc.getElementsByTagName("svg").item(0), "-webkit-user-select", "none");
                }
                //防止内容过分复杂时getElementById失败
                setTimeout(function() {
                    self._init();
                    callback.call(self, self);
                }, options.delay || 1e3);
            }
        }
    }
    Paper.prototype = {
        constructor: Paper,
        _init: function() {
            var self = this;
            //自定义控件
            this.libs = this.libs || {};
            var keyMap = {
                27: "esc",
                13: "enter",
                37: "left",
                38: "up",
                39: "right",
                40: "down",
                //ASV
                61: isSVG ? "" : "=",
                //Chrome
                187: isSVG ? "=" : "",
                45: isSVG ? "" : "-",
                189: isSVG ? "-" : "",
                127: isSVG ? "" : "del",
                46: isSVG ? "del" : ""
            };
            this.keyHandlers = {};
            this.root.addEventListener("keydown", function(e) {
                var key = keyMap[e.keyCode];
                if (key) {
                    var data = self.keyHandlers[key];
                    for (var name in data) {
                        if (data.hasOwnProperty(name)) {
                            data[name](e);
                        }
                    }
                }
            }, false);
            this.initPosition();
            this.on("resize", function() {
                self.initPosition();
            });
            this.on("reset", function() {
                self.initPosition();
            });
        },
        /*
     *DOM
     */
        get: function(id) {
            return this.doc.getElementById(id);
        },
        //create node
        $: function(node) {
            if (typeof node == "string") {
                try {
                    //firefox中必须使用namespace,而asv中没有此方法
                    node = this.doc.createElementNS("http://www.w3.org/2000/svg", node);
                } catch (e) {
                    node = this.doc.createElement(node);
                }
            }
            var args = Array.prototype.slice.call(arguments, 1);
            //自动生成id
            if (!this.attr(node, "id")) {
                if (args.length === 0) {
                    //没有指定任何属性
                    args.push({});
                } else if (typeof args[0] == "string") {
                    //指定单一属性
                    var tempObj = {};
                    tempObj[args[0]] = args[1];
                    args = [ tempObj ];
                }
                var obj = args[0];
                if (!obj.id) {
                    obj.id = nextId();
                }
            }
            args.unshift(node);
            this.attr.apply(this, args);
            return node;
        },
        attr: function(node, name, value) {
            if (typeof name == "object") {
                for (var n in name) {
                    if (name.hasOwnProperty(n)) {
                        this.attr(node, n, name[n]);
                    }
                }
            } else {
                if (value === undefined) {
                    if (node.hasAttribute(name)) {
                        return node.getAttribute(name);
                    } else {
                        return null;
                    }
                }
                if (name.indexOf(":") >= 0) {
                    node.setAttributeNS(NS[name.split(":")[0]], name, value);
                } else {
                    node.setAttribute(name, value);
                }
            }
            return node;
        },
        removeAttr: function(node) {
            var attrs = Array.prototype.slice.call(arguments, 1);
            for (var i = attrs.length - 1; i >= 0; i--) {
                node.removeAttribute(attrs[i]);
            }
            return node;
        },
        style: function(node, name, value) {
            if (typeof name == "object") {
                for (var n in name) {
                    if (name.hasOwnProperty(n)) {
                        this.style(node, n, name[n]);
                    }
                }
            } else {
                var style = node.getAttribute("style");
                style = this._style(style, name, value);
                if (value === undefined) {
                    return style;
                }
                node.setAttribute("style", style);
            }
            return node;
        },
        _style: function(style, name, value) {
            if (!style) {
                style = "";
            }
            var lst = style.split(";");
            var i, w;
            if (value === undefined) {
                //get
                for (i = lst.length - 1; i >= 0; i--) {
                    w = lst[i].split(":");
                    if (name === w[0].replace(rPreBlank, "")) {
                        //IE10有前导空格
                        return w[1].replace(rPreBlank, "");
                    }
                }
                return null;
            } else {
                //set
                var result = [];
                var found;
                for (i = lst.length - 1; i >= 0; i--) {
                    w = lst[i].split(":");
                    if (name === w[0]) {
                        found = true;
                        w[1] = value;
                    }
                    if (lst[i] !== "") {
                        result.unshift(w[0] + ":" + w[1]);
                    }
                }
                if (!found) {
                    result.push(name + ":" + value);
                }
                return result.join(";");
            }
        },
        /*
     *Element
     */
        createElement: function(node) {
            if (typeof node == "string") {
                node = this.get(node);
            }
            return new Element(this, node);
        },
        rect: function(x, y, w, h, r) {
            var node = this.$("rect", {
                x: x || 0,
                y: y || 0,
                width: w || 0,
                height: h || 0,
                rx: r || 0,
                ry: r || 0,
                fill: "none",
                stroke: "#000"
            });
            return this.createElement(node);
        },
        circle: function(x, y, r) {
            var node = this.$("circle", {
                cx: x || 0,
                cy: y || 0,
                r: r || 0,
                fill: "none",
                stroke: "#000"
            });
            return this.createElement(node);
        },
        image: function(src, x, y, w, h) {
            var node = this.$("image", {
                x: x || 0,
                y: y || 0,
                width: w || 0,
                height: h || 0,
                "xlink:href": src || "about:blank"
            });
            return this.createElement(node);
        },
        text: function(text, x, y, fontSize) {
            var node = this.$("text", {
                x: x || 0,
                y: y || 0,
                "font-size": fontSize || 12,
                style: "font-family:SimSun;"
            });
            node.appendChild(this.doc.createTextNode(text || ""));
            return this.createElement(node);
        },
        path: function(d, color) {
            var node = this.$("path", {
                d: d || "M0,0",
                fill: "none",
                stroke: color || "#000"
            });
            return this.createElement(node);
        },
        /*
     *control
     */
        //常用于事件响应中
        getControlNode: function(node) {
            while (node) {
                if (node.control) return node;
                node = node.parentNode;
            }
            return null;
        },
        createControl: function(node, type) {
            if (type === undefined) {
                type = node.getAttribute("data-type");
            }
            var Type = this.libs[type] || Control;
            var control = new Type(this, node);
            control.type = type;
            return control;
        },
        /*
     *helper
     */
        setViewBox: function(x, y, w, h) {
            if (!this.needZoomLimit) {
                this._setViewBox(x, y, w, h);
            } else {
                var oldWidth = this._viewBox[2];
                if (w < oldWidth && w < this.width / this.maxZoom) {
                    this.trigger("zoommax");
                } else if (w > oldWidth && w > this.width / this.minZoom) {
                    this.trigger("zoommin");
                } else {
                    this._setViewBox(x, y, w, h);
                }
            }
            return this;
        },
        _setViewBox: function(x, y, w, h) {
            var box = x;
            if (Object.prototype.toString.call(x) != "[object Array]") {
                box = [ x, y, w, h ];
            }
            this.$(this.root, {
                viewBox: box.join(" ")
            });
            this._viewBox = box;
            //相对svg原图的缩放比例
            this.factor = this.width / w;
            this.trigger("zoom", this.factor);
            return this;
        },
        initPosition: function() {
            if (this.wrapperId) {
                var node = this.get(this.wrapperId);
                if (node) {
                    //将node放到最大并居中
                    var bBox = node.getBBox();
                    var level = Math.min(this.width / bBox.width, this.height / bBox.height);
                    this.setCenter(node, level);
                    return;
                }
            }
            this._setViewBox(0, 0, this.width, this.height);
        },
        setCenter: function(node, level) {
            level = level || 1;
            var p = this.getPos(node, "center");
            this._setCenter(p.x, p.y, level);
            return this;
        },
        _setCenter: function(x, y, level) {
            level = level || 1;
            var w = this.width / level, h = this.height / level;
            this._setViewBox(x - w / 2, y - h / 2, w, h);
            return this;
        },
        //当前坐标系下的位置
        getRelativePos: function(node, pos, factorY) {
            if (typeof pos === "string") {
                var factors = posFactors[pos];
                if (factors && factors.length) {
                    factorX = factors[0];
                    factorY = factors[1];
                } else {
                    throw new Error(pos + "is not existed!");
                }
            } else {
                factorX = pos;
            }
            var bBox = node.getBBox();
            var x = bBox.x + bBox.width * factorX, y = bBox.y + bBox.height * factorY;
            return {
                x: x,
                y: y
            };
        },
        //根节点坐标系下的位置
        getPos: function(node, pos, factorY) {
            var p = this.getRelativePos(node, pos, factorY), cx = p.x, cy = p.y;
            var ctm = node.getTransformToElement(this.root);
            var x = ctm.a * cx + ctm.c * cy + ctm.e * 1, y = ctm.b * cx + ctm.d * cy + ctm.f * 1;
            return {
                x: x,
                y: y
            };
        },
        getDef: function() {
            var def;
            var defs = this.doc.getElementsByTagName("defs");
            if (defs.length) {
                def = defs.item(0);
            } else {
                def = this.$("defs");
                this.root.appendChild(def);
            }
            return def;
        },
        //鼠标坐标转化为svg点坐标
        getSvgPoint: function(ex, ey) {
            var box = this._viewBox, x0 = box[0], y0 = box[1], w0 = box[2], h0 = box[3], x = x0 + w0 / this.width * ex, y = y0 + h0 / this.height * ey;
            return {
                x: x,
                y: y
            };
        },
        cursor: function(type) {
            this.root.setAttribute("cursor", type);
        },
        //仅供调试使用
        content: function(node) {
            node = node || this.root;
            return isSVG ? node : this.embed.window.printNode(node);
        }
    };
    Events.mixTo(Paper);
    //event
    var preventDefault = function() {
        this.returnValue = false;
    }, preventTouch = function() {
        return this.originalEvent.preventDefault();
    }, stopPropagation = function() {
        this.cancelBubble = true;
    }, stopTouch = function() {
        return this.originalEvent.stopPropagation();
    }, supportsTouch = "createTouch" in document, events = "click dblclick mousedown mousemove mouseout mouseover mouseup touchstart touchmove touchend touchcancel".split(" "), touchMap = {
        mousedown: "touchstart",
        mousemove: "touchmove",
        mouseup: "touchend"
    }, addEvent = function(type, fn, element) {
        //分别对应Svg和Element两种情形
        var obj = this.node || this.root, realName = supportsTouch && touchMap[type] ? touchMap[type] : type, f = function(e) {
            var x = e.clientX, y = e.clientY;
            if (supportsTouch && touchMap.hasOwnProperty(type)) {
                for (var i = 0, ii = e.targetTouches && e.targetTouches.length; i < ii; i++) {
                    if (e.targetTouches[i].target == obj) {
                        var olde = e;
                        e = e.targetTouches[i];
                        e.originalEvent = olde;
                        e.preventDefault = preventTouch;
                        e.stopPropagation = stopTouch;
                        break;
                    }
                }
            }
            return fn.call(element, e, x, y);
        };
        obj.addEventListener(realName, f, false);
        return function() {
            obj.removeEventListener(realName, f, false);
            return true;
        };
    }, _onEvent = function(_event, eventName, fn, scope) {
        if (typeof fn == "function") {
            this.events = this.events || [];
            this.events.push({
                name: eventName,
                f: fn,
                unbind: _event.call(this, eventName, fn, scope || this)
            });
        }
        return this;
    }, _offEvent = function(eventName, fn) {
        //TODO: 如果没有指定fn，则删除所有eventName指定的事件
        var events = this.events || [], l = events.length;
        while (l--) if (events[l].name == eventName && events[l].f == fn) {
            events[l].unbind();
            events.splice(l, 1);
            !events.length && delete this.events;
            return this;
        }
        return this;
    };
    for (var i = events.length; i--; ) {
        (function(eventName) {
            Paper.prototype[eventName] = function(fn, scope) {
                return _onEvent.call(this, addEvent, eventName, fn, scope);
            };
            Paper.prototype["un" + eventName] = function(fn) {
                return _offEvent.call(this, eventName, fn);
            };
        })(events[i]);
    }
    //mousewheel
    var mousewheelEvent = function() {
        if (isSVG) {
            return function(type, fn, element) {
                var obj = this.root;
                var f = function(e) {
                    var x = e.clientX, y = e.clientY, direction;
                    if (e.wheelDelta) {
                        direction = e.wheelDelta > 0 ? "up" : "down";
                    }
                    if (e.detail) {
                        direction = e.detail < 0 ? "up" : "down";
                    }
                    return fn.call(element, e, x, y, direction);
                };
                obj.addEventListener(type, f, false);
                return function() {
                    obj.removeEventListener(type, f, false);
                    return true;
                };
            };
        } else {
            return function(type, fn, element) {
                //asv貌似没有实现mousewheel事件,因此冒泡到父元素
                var obj = this.container;
                var f = function(e) {
                    e = e || window.event;
                    var scrollY = document.documentElement.scrollTop || document.body.scrollTop, scrollX = document.documentElement.scrollLeft || document.body.scrollLeft, deltaX = obj.getBoundingClientRect().left, deltaY = obj.getBoundingClientRect().top, x = e.clientX - deltaX - scrollX, y = e.clientY - deltaY - scrollY, direction = e.wheelDelta > 0 ? "up" : "down";
                    e.preventDefault = e.preventDefault || preventDefault;
                    e.stopPropagation = e.stopPropagation || stopPropagation;
                    return fn.call(element, e, x, y, direction);
                };
                obj.attachEvent("on" + type, f);
                var detacher = function() {
                    obj.detachEvent("on" + type, f);
                    return true;
                };
                return detacher;
            };
        }
    }();
    Paper.prototype.mousewheel = function(fn, scope) {
        var support = document.onmousewheel !== undefined ? "mousewheel" : "DOMMouseScroll";
        _onEvent.call(this, mousewheelEvent, support, fn, scope);
    };
    Paper.prototype.unmousewheel = function(fn) {
        _offEvent.call(this, "mousewheel", fn);
    };
    //keydown
    var keydownEvent = function(type, fn, element) {
        var self = this;
        var key = type.split(" ")[1], f = function(e) {
            return fn.call(element, e);
        };
        this.keyHandlers[key] = this.keyHandlers[key] || [];
        this.keyHandlers[key].push(f);
        return function() {
            var lst = self.keyHandlers[key];
            for (var i = lst.length - 1; i >= 0; i--) {
                if (lst[i] === f) {
                    lst.splice(i, 1);
                }
            }
            return true;
        };
    }, createKeydownName = function(key) {
        return "keydown " + key;
    };
    Paper.prototype.keydown = function(key, fn, scope) {
        key = createKeydownName(key);
        _onEvent.call(this, keydownEvent, key, fn, scope);
    };
    Paper.prototype.unkeydown = function(key, fn) {
        key = createKeydownName(key);
        _offEvent.call(this, key, fn);
    };
    module.exports = Paper;
});

define("ikj/svg/1.0.0/element-debug", [ "arale/class/1.0.0/class-debug" ], function(require, exports, module) {
    var Class = require("arale/class/1.0.0/class-debug");
    var Element = Class.create({
        initialize: function(paper, node) {
            this.paper = paper;
            this.node = node;
            //TODO:用途说明
            this.id = node.id;
        },
        /*
     *dom
     */
        appendTo: function(node) {
            node = node || this.paper.root;
            node = node.node || node;
            node.appendChild(this.node);
            return this;
        },
        prependTo: function(node) {
            node = node || this.paper.root;
            node = node.node || node;
            var childNodes = node.childNodes;
            if (childNodes.length > 0) {
                node.insertBefore(this.node, childNodes.item(0));
            } else {
                node.appendChild(this.node);
            }
            return this;
        },
        insertBefore: function(node) {
            node = node.node || node;
            node.parentNode.insertBefore(this.node, node);
            return this;
        },
        attr: function() {
            var args = Array.prototype.slice.call(arguments);
            args.unshift(this.node);
            var result = this.paper.attr.apply(this.paper, args);
            //getAttr
            if (result !== this.node) {
                return result;
            }
            return this;
        },
        removeAttr: function() {
            var args = Array.prototype.slice.call(arguments);
            args.unshift(this.node);
            var result = this.paper.removeAttr.apply(this.paper, args);
            return this;
        },
        style: function() {
            var args = Array.prototype.slice.call(arguments);
            args.unshift(this.node);
            var result = this.paper.style.apply(this.paper, args);
            //getAttr
            if (result !== this.node) {
                return result;
            }
            return this;
        },
        text: function(value) {
            if (this.node.tagName.toLowerCase() != "text") {
                throw new Error("text() can only be used in text Element!");
            }
            if (value === undefined) {
                if (this.node.childNodes.length) {
                    return this.node.childNodes.item(0).nodeValue;
                } else {
                    return "";
                }
            } else {
                if (this.node.childNodes.length) {
                    this.node.childNodes.item(0).nodeValue = value;
                } else {
                    var node = this.paper.doc.createTextNode(value);
                    this.node.appendChild(node);
                }
                return this;
            }
        },
        show: function() {
            //style会覆盖attr
            this.style("opacity", 1);
            return this;
        },
        hide: function() {
            this.style("opacity", 0);
            return this;
        },
        empty: function() {
            var parentNode = this.node, nodes = parentNode.childNodes;
            for (var i = nodes.length - 1; i >= 0; i--) {
                parentNode.removeChild(nodes.item(i));
            }
            return this;
        },
        remove: function() {
            this.node.parentNode.removeChild(this.node);
        },
        setCenter: function() {
            var args = Array.prototype.slice.call(arguments);
            args.unshift(this.node);
            var result = this.paper.setCenter.apply(this.paper, args);
            return this;
        },
        getPos: function(pos, factorY) {
            var args = Array.prototype.slice.call(arguments);
            args.unshift(this.node);
            return this.paper.getPos.apply(this.paper, args);
        },
        //开始动画
        animate: function(attr, to, options) {
            this.paper.animate.add(this.node, attr, to, options);
            return this;
        },
        //恢复原状
        restore: function() {
            this.paper.animate.restore(this.node);
            return this;
        }
    });
    (function(proto) {
        var events = "click dblclick mousedown mousemove mouseout mouseover mouseup touchstart touchmove touchend touchcancel".split(" ");
        for (var i = events.length; i--; ) {
            (function(eventName) {
                proto[eventName] = function(fn, scope) {
                    return this.paper[eventName].apply(this, arguments);
                };
                proto["un" + eventName] = function(fn) {
                    return this.paper["un" + eventName].apply(this, arguments);
                };
            })(events[i]);
        }
        proto.hover = function(f_in, f_out, scope_in, scope_out) {
            return this.mouseover(f_in, scope_in).mouseout(f_out, scope_out || scope_in);
        };
        proto.unhover = function(f_in, f_out) {
            return this.unmouseover(f_in).unmouseout(f_out);
        };
    })(Element.prototype);
    module.exports = Element;
});

define("ikj/svg/1.0.0/libs/control-debug", [ "ikj/svg/1.0.0/element-debug", "arale/class/1.0.0/class-debug" ], function(require, exports, module) {
    var Element = require("ikj/svg/1.0.0/element-debug");
    var Control = Element.extend({
        //TODO: node为element的情况
        initialize: function(paper, node) {
            Control.superclass.initialize.apply(this, arguments);
            if (node.nodeType == 1) {
                //TODO:将所有data-×自动变为属性
                if (node.hasAttribute("data-image")) {
                    this.image = node.getAttribute("data-image");
                }
                if (node.hasAttribute("data-title")) {
                    this.title = node.getAttribute("data-title");
                }
                this.parseParts(node);
                //dom中添加标识
                node.control = true;
            } else {
                return this._createParts(node);
            }
        },
        _createParts: function(options) {
            var id = options.id || "", x = options.x || 0, y = options.y || 0, size = options.size || 1;
            var node = this.paper.$("g", {
                id: id,
                transform: "translate(" + x + "," + y + ") scale(" + size + ")"
            });
            this.createParts(node, options);
            this.node = node;
            //动态生成的control中的group实际上为一个element，但是伪装成control
            node.control = true;
            var element = new Element(this, node);
            return element;
        },
        //供子类动态创建组件时进行重载
        createParts: function(node, options) {
            this.parts = {};
        },
        parseParts: function(parentNode) {
            //TODO:深层解析
            this.parts = {};
            var nodes = parentNode.childNodes;
            for (var i = nodes.length - 1; i >= 0; i--) {
                var node = nodes.item(i);
                //ELEMENT_NODE
                if (node.nodeType == 1) {
                    if (node.hasAttribute("data-name")) {
                        var name = node.getAttribute("data-name");
                        //每部分也是element
                        this.parts[name] = new Element(this.paper, node);
                    }
                }
            }
        }
    });
    module.exports = Control;
});

define("ikj/svg/1.0.0/move-debug", [], function(require, exports, module) {
    var fn = function(paper) {
        var hasStarted, isDragging, startX, startY, startBoxX, startBoxY;
        return {
            start: function() {
                if (hasStarted) return;
                paper.mousedown(this.grab, this);
                paper.mousemove(this.drag, this);
                paper.mouseup(this.drop, this);
                hasStarted = true;
            },
            stop: function() {
                paper.unmousedown(this.grab);
                paper.unmousemove(this.drag);
                paper.unmouseup(this.drop);
                hasStarted = false;
            },
            grab: function(e, x, y) {
                //鼠标左键,ASV不支持e.which
                if (e.button === 0) {
                    //避免鼠标变为text-selection
                    //会导致 paper.root.addEventListener('keydown', function(e) { console.log(e); }, false);失效
                    //e.preventDefault();
                    isDragging = true;
                    //Chrome下无法保持cursor
                    paper.cursor("move");
                    var box = paper._viewBox;
                    startX = x;
                    startY = y;
                    startBoxX = box[0];
                    startBoxY = box[1];
                }
            },
            drag: function(e, x, y) {
                if (!isDragging) return;
                var box = paper._viewBox, w = box[2], h = box[3], delX = (x - startX) / paper.factor, delY = (y - startY) / paper.factor;
                paper.setViewBox(startBoxX - delX, startBoxY - delY, w, h);
            },
            drop: function(e) {
                isDragging = false;
                paper.cursor("default");
            }
        };
    };
    module.exports = fn;
});

define("ikj/svg/1.0.0/zoom-debug", [], function(require, exports, module) {
    var fn = function(paper) {
        return {
            start: function() {
                paper.mousewheel(this.zoom, this);
            },
            stop: function() {
                paper.unmousewheel(this.zoom);
            },
            zoom: function(e, x, y, direction) {
                var box = paper._viewBox, x0 = box[0], y0 = box[1], w0 = box[2], h0 = box[3], f, xi, yi, wi, hi;
                if (direction == "up") {
                    f = 1.1;
                } else {
                    f = .9;
                }
                var svgPoint = paper.getSvgPoint(x, y);
                x = svgPoint.x;
                xi = x - (x - x0) / f;
                y = svgPoint.y;
                yi = y - (y - y0) / f;
                wi = w0 / f;
                hi = h0 / f;
                paper.setViewBox(xi, yi, wi, hi);
                e.preventDefault();
            }
        };
    };
    module.exports = fn;
});

define("ikj/svg/1.0.0/drag-debug", [], function(require, exports, module) {
    var fn = function(paper) {
        var node, hasStarted, isDragging;
        return {
            start: function() {
                if (hasStarted) return;
                paper.mousedown(this.grab, this);
                paper.mousemove(this.drag, this);
                paper.mouseup(this.drop, this);
                hasStarted = true;
            },
            stop: function() {
                paper.unmousedown(this.grab);
                paper.unmousemove(this.drag);
                paper.unmouseup(this.drop);
                hasStarted = false;
            },
            grab: function(e, x, y) {
                //鼠标左键,ASV不支持e.which
                if (e.button === 0) {
                    node = paper.getControlNode(e.target);
                    console.log(e, node);
                    if (!node) return;
                    //避免鼠标变为text-selection
                    e.preventDefault();
                    isDragging = true;
                    paper.cursor("move");
                }
            },
            drag: function(e, x, y) {
                if (!isDragging) return;
                var p = paper.getSvgPoint(x, y);
                //TODO: "translate(300,300) scale(1)"
                node.setAttribute("transform", "translate(" + p.x + "," + p.y + ")");
            },
            drop: function(e) {
                isDragging = false;
                paper.cursor("default");
            }
        };
    };
    module.exports = fn;
});

define("ikj/svg/1.0.0/slider-debug", [ "$-debug", "gallery/handlebars/1.0.0/handlebars-debug" ], function(require, exports, module) {
    var $ = require("$-debug"), handlebars = require("gallery/handlebars/1.0.0/handlebars-debug"), tpl = require("ikj/svg/1.0.0/slider-debug.tpl");
    var fn = function(paper, options) {
        var node = handlebars.compile(tpl)({
            top: options.sliderTop || 20,
            left: options.sliderLeft || 20,
            imgPath: options.sliderImgPath || "./images"
        });
        var $slider = $(node).appendTo(document.body), $left = $slider.find("[data-role=left]"), $right = $slider.find("[data-role=right]"), $up = $slider.find("[data-role=up]"), $down = $slider.find("[data-role=down]"), $reset = $slider.find("[data-role=reset]"), $more = $slider.find("[data-role=more]"), $less = $slider.find("[data-role=less]");
        var moveFactor = 10;
        $left.click(function() {
            var box = paper._viewBox;
            paper.setViewBox(box[0] + moveFactor, box[1], box[2], box[3]);
        });
        $right.click(function() {
            var box = paper._viewBox;
            paper.setViewBox(box[0] - moveFactor, box[1], box[2], box[3]);
        });
        $up.click(function() {
            var box = paper._viewBox;
            paper.setViewBox(box[0], box[1] + moveFactor, box[2], box[3]);
        });
        $down.click(function() {
            var box = paper._viewBox;
            paper.setViewBox(box[0], box[1] - moveFactor, box[2], box[3]);
        });
        $reset.click(function() {
            paper.trigger("reset");
        });
        paper.keydown("left", function(e) {
            var box = paper._viewBox;
            paper.setViewBox(box[0] + moveFactor, box[1], box[2], box[3]);
            e.preventDefault();
        });
        paper.keydown("right", function(e) {
            var box = paper._viewBox;
            paper.setViewBox(box[0] - moveFactor, box[1], box[2], box[3]);
            e.preventDefault();
        });
        paper.keydown("up", function(e) {
            var box = paper._viewBox;
            paper.setViewBox(box[0], box[1] + moveFactor, box[2], box[3]);
            e.preventDefault();
        });
        paper.keydown("down", function(e) {
            var box = paper._viewBox;
            paper.setViewBox(box[0], box[1] - moveFactor, box[2], box[3]);
            e.preventDefault();
        });
        function zoom(factor) {
            var p = paper.getSvgPoint(paper.width / 2, paper.height / 2);
            paper._setCenter(p.x, p.y, paper.factor * factor);
        }
        $more.click(function() {
            zoom(1.1);
        });
        $less.click(function() {
            zoom(.9);
        });
        paper.keydown("=", function(e) {
            zoom(1.1);
            e.preventDefault();
        });
        paper.keydown("-", function(e) {
            zoom(.9);
            e.preventDefault();
        });
    };
    module.exports = fn;
});

define("ikj/svg/1.0.0/slider-debug.tpl", [], '<div style="position:absolute;top:{{top}}px;left:{{left}}px;">\n  <div class="png" style="width:59px;height:65px;background: url(\'{{imgPath}}/controls.png\');">\n    <div data-role="up" style="position: absolute; left: 20px; top: 0; width: 18px; height: 18px; cursor: pointer;" title="上移"></div>\n    <div data-role="left" style="position: absolute; left: 0; top: 20px; width: 18px; height: 18px; cursor: pointer;" title="左移"></div>\n    <div data-role="right" style="position: absolute; left: 40px; top: 20px; width: 18px; height: 18px; cursor: pointer;" title="右移"></div>\n    <div data-role="down" style="position: absolute; left: 20px; top: 40px; width: 18px; height: 18px; cursor: pointer;" title="下移"></div>\n    <div data-role="reset" style="position: absolute; left: 20px; top: 20px; width: 18px; height: 18px; cursor: pointer;" title="重置"></div>\n  </div>\n  <div class="png" data-role="more" style="position: absolute; left: 18px; top: 88px; width: 22px; height: 24px; cursor: pointer;background: url(\'{{imgPath}}/controls.png\') -18px -62px;" title="放大"></div>\n  <div class="png" data-role="less" style="position: absolute; left: 18px; top: 112px; width: 22px; height: 24px; cursor: pointer;background: url(\'{{imgPath}}/controls.png\') -18px -360px;" title="缩小"></div>\n</div>\n');

define("ikj/svg/1.0.0/animate-debug", [ "arale/easing/1.0.0/easing-debug", "$-debug" ], function(require, exports, module) {
    var easing = require("arale/easing/1.0.0/easing-debug");
    var fn = function(paper) {
        var isRunning = false;
        var animations = {}, requestAnimFrame = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function(callback) {
            setTimeout(callback, 16);
        };
        var run = function() {
            if (!isRunning) return;
            var nowTime = new Date();
            for (var id in animations) {
                var anims = animations[id];
                for (var i = anims.length - 1; i >= 0; i--) {
                    var anim = anims[i];
                    var per = Math.min(1, (nowTime - anim.startTime) / anim.ms);
                    //到完成时间
                    if (per >= 1) {
                        //如果可以恢复
                        if (anim.toggle) {
                            anim.startTime = nowTime;
                            var temp = anim.to;
                            anim.to = anim.from;
                            anim.from = temp;
                            //如果是有限的
                            if (typeof anim.toggle == "number") {
                                //如果处于返程
                                if (!anim.hasToggled) {
                                    anim.toggle--;
                                }
                                anim.hasToggled = !anim.hasToggled;
                            }
                        } else {
                            //否则动画停止
                            anims.splice(i, 1);
                        }
                        continue;
                    }
                    var now = anim.from + (anim.to - anim.from) * anim.easing(per);
                    //console.log(now);
                    paper.style(anim.node, anim.attr, now);
                }
            }
            requestAnimFrame(run);
        };
        return {
            //start: function() {
            //run();
            //},
            //停止所有动画或者单个node动画
            stop: function(node) {
                if (!node) {
                    isRunning = false;
                    animations = {};
                } else {
                    var id = paper.attr(node, "id");
                    animations[id] = [];
                }
            },
            //立即停止动画并恢复原状
            restore: function(node) {
                var id = paper.attr(node, "id");
                var anims = animations[id];
                if (anims) {
                    for (var i = anims.length - 1; i >= 0; i--) {
                        var anim = anims[i];
                        paper.style(anim.node, anim.attr, anim.origin);
                    }
                    animations[id] = [];
                }
            },
            add: function(node, attr, to, options) {
                options = options || {};
                var from = paper.style(node, attr);
                from = parseFloat(from);
                var anim = {
                    node: node,
                    attr: attr,
                    origin: from,
                    //原始值
                    from: from,
                    to: to,
                    startTime: new Date(),
                    ms: options.ms || 1e3,
                    //动画执行一次持续时间
                    toggle: options.toggle,
                    //设为true表示会恢复到原状，如果为数字则可以恢复n次
                    easing: easing[options.easing] || easing.easeNone
                };
                var id = paper.attr(node, "id");
                if (!id) {
                    throw new Error("animated node must have id:" + node);
                }
                animations[id] = animations[id] || [];
                animations[id].push(anim);
                if (!isRunning) {
                    isRunning = true;
                    run();
                }
                return this;
            },
            _animations: function() {
                return animations;
            }
        };
    };
    module.exports = fn;
});

define("ikj/svg/1.0.0/libs/layer-debug", [ "ikj/svg/1.0.0/libs/control-debug", "ikj/svg/1.0.0/element-debug", "arale/class/1.0.0/class-debug" ], function(require, exports, module) {
    var Control = require("ikj/svg/1.0.0/libs/control-debug");
    var Fn = Control.extend({
        createParts: function(node, options) {
            console.log("not implemented");
        },
        show: function() {
            this.style("display", "inline");
            return this;
        },
        hide: function() {
            this.style("display", "none");
            return this;
        }
    });
    module.exports = Fn;
});

define("ikj/svg/1.0.0/libs/pic-debug", [ "ikj/svg/1.0.0/libs/control-debug", "ikj/svg/1.0.0/element-debug", "arale/class/1.0.0/class-debug" ], function(require, exports, module) {
    var Control = require("ikj/svg/1.0.0/libs/control-debug");
    var Fn = Control.extend({
        createParts: function(node, options) {
            //pic,error
            var parts = this.parts = {};
            var src = options.src || "about:blank", width = options.width || 20, height = options.height || 20;
            parts.pic = paper.image(src, -width / 2, -height / 2, width, height).appendTo(node);
            var x0 = 0, y0 = 0, x1 = width / 2, y1 = height / 2, path = "M" + x0 + "," + y0 + "L" + x1 + "," + y1 + "M" + x1 + "," + y0 + "L" + x0 + "," + y1;
            parts.error = paper.path(path).attr({
                stroke: "red",
                opacity: 0
            }).appendTo(node);
        },
        setError: function() {
            this.attr("opacity", 1);
            this.parts.error.attr("opacity", 1);
            return this;
        },
        setInvalid: function() {
            this.attr("opacity", .5);
            this.parts.error.attr("opacity", 0);
            return this;
        },
        setNormal: function() {
            this.attr("opacity", 1);
            this.parts.error.attr("opacity", 0);
            return this;
        }
    });
    module.exports = Fn;
});

define("ikj/svg/1.0.0/libs/fan-debug", [ "ikj/svg/1.0.0/libs/control-debug", "ikj/svg/1.0.0/element-debug", "arale/class/1.0.0/class-debug" ], function(require, exports, module) {
    var Control = require("ikj/svg/1.0.0/libs/control-debug");
    var Fan = Control.extend({
        createParts: function(node, options) {
            //wing
            var parts = this.parts = {};
            var color = options.color || "green";
            parts.wing = this.paper.path("m 0.2788437,-24.827646 c -7.1045246,-0.224771 -13.2753147,6.808918 -12.3134957,13.818964 0.565051,5.8514967 5.7882227,11.03467102 11.70512145,11.1891868 1.21604468,-1.1593433 0.40424885,-3.1172217 0.60837425,-4.667179 0,-6.7803238 0,-13.5606478 0,-20.3409718 z m 0,24.98797724 c 8.2117698,0 16.4235393,0 24.6353093,0 C 25.166153,-6.8257936 18.466569,-13.15038 11.481234,-12.315294 5.3130452,-11.803486 0.07609429,-6.0509848 0.2788437,0.16033124 z m 0,0 c 0,8.34040196 0,16.68080376 0,25.02120576 C 7.4098013,25.410382 13.56657,18.307012 12.551166,11.286637 11.923154,5.2607109 6.4540212,-0.03871538 0.2788437,0.16033124 z m 0,0 c -8.2226892,0 -16.4453787,0 -24.6680677,0 C -24.665465,7.3545173 -17.537933,13.834422 -10.377992,12.596475 -4.4407327,11.78642 0.47505319,6.1921747 0.2788437,0.16033124 z").attr({
                fill: color
            }).appendTo(node);
            this.paper.path("M 2.5918867,0.52579924 C 2.8695438,4.1867099 -3.6136881,2.7032789 -1.846105,-0.46769561 -0.95028185,-2.7028532 2.7399051,-1.8583247 2.5918867,0.52579924 z").attr({
                fill: color
            }).appendTo(node);
        },
        setColor: function(color) {
            this.parts.wing.style("fill", color);
            return this;
        },
        //t毫秒转动一周
        rotate: function(t) {
            //if (!isIE9) {
            //var animate = getRotateAnimate(this.paper);
            //this.parts.wing.node.appendChild(animate);
            //} else {
            t = t || 1e3;
            this._rotateInIE9(t);
            //}
            return this;
        },
        stopRotate: function() {
            //if (!isIE9) {
            //this.parts.wing.empty();
            //} else {
            this._canRotate = false;
            //}
            return this;
        },
        _rotateInIE9: function(t) {
            var element = this.parts.wing;
            var pos = this.paper.getRelativePos(element.node, "center");
            this._canRotate = true;
            var self = this, a = 0, interval = 25, step = 360 * interval / t;
            function r() {
                if (self._canRotate) {
                    a = a < 360 ? a + step : 0;
                    element.attr("transform", "rotate(" + a + " " + pos.x + " " + pos.y + ")");
                    setTimeout(r, interval);
                } else {
                    element.attr("transform", "");
                }
            }
            r();
        }
    });
    var _rotateNode;
    function getRotateAnimate(paper) {
        if (!_rotateNode) {
            _rotateNode = paper.$("animateTransform", {
                attributeName: "transform",
                type: "rotate",
                dur: "3",
                from: "0",
                to: "360",
                repeatCount: "indefinite"
            });
            return _rotateNode;
        } else {
            return _rotateNode.cloneNode(true);
        }
    }
    //IE9,IE10
    function isIE9() {
        //IE8 "4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC
        //IE9 "5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)"
        //IE10 "5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/6.0)"
        return /MSIE 9/i.test(navigator.appVersion);
    }
    module.exports = Fan;
});

define("ikj/svg/1.0.0/libs/symbol-debug", [ "ikj/svg/1.0.0/libs/control-debug", "ikj/svg/1.0.0/element-debug", "arale/class/1.0.0/class-debug" ], function(require, exports, module) {
    var Control = require("ikj/svg/1.0.0/libs/control-debug");
    var Fn = Control.extend({
        createParts: function(node, options) {
            console.log("not implemented");
        },
        setStatus: function(value) {
            this.parts.status.text(value);
            return this;
        },
        setCross: function() {
            this.parts.cross.style("opacity", 1);
            this.parts.check.style("opacity", 0);
            return this;
        },
        setCheck: function() {
            this.parts.check.style("opacity", 1);
            this.parts.cross.style("opacity", 0);
            return this;
        },
        setNormal: function() {
            this.parts.check.style("opacity", 0);
            this.parts.cross.style("opacity", 0);
            return this;
        }
    });
    module.exports = Fn;
});

define("ikj/svg/1.0.0/libs/sensor-debug", [ "ikj/svg/1.0.0/libs/control-debug", "ikj/svg/1.0.0/element-debug", "arale/class/1.0.0/class-debug" ], function(require, exports, module) {
    var Control = require("ikj/svg/1.0.0/libs/control-debug");
    var Fn = Control.extend({
        createParts: function(node, options) {
            //indicator,name,value
            var parts = this.parts = {};
            var size = options.size || 1, color = options.color || "green";
            var width = size * 30, height = width / 2, r = width / 6;
            parts.indicator = paper.rect(-width / 2, -height / 2, width, height, width / 3).attr({
                fill: color,
                stroke: "#333",
                "stroke-width": .5
            }).appendTo(node);
            parts.name = paper.text("---", 0, r, r * 2.4).appendTo(node);
            parts.value = paper.text("0.00", width, r / 2, r * 2).appendTo(node);
        },
        setColor: function(color) {
            this.parts.indicator.style({
                fill: color,
                stroke: color
            });
            return this;
        },
        getColor: function(color) {
            return this.parts.indicator.style("fill");
        },
        setFocus: function(options) {
            options = options || {};
            var strokeWidth = options.strokeWidth || 2;
            var fillOpacity = options.fillOpacity || 1;
            this.parts.indicator.animate("stroke-width", strokeWidth, {
                toggle: 3,
                easing: "easeOut"
            }).animate("fill-opacity", fillOpacity, {
                toggle: 3,
                easing: "easeOut"
            });
            return this;
        },
        loseFocus: function(color) {
            this.parts.indicator.restore();
            return this;
        }
    });
    (function(proto) {
        var names = "Value Name".split(" ");
        for (var i = names.length; i--; ) {
            (function(name) {
                proto["set" + name] = function(value) {
                    this.parts[name.toLowerCase()].text(value);
                    return this;
                };
                proto["get" + name] = function() {
                    return this.parts[name.toLowerCase()].text();
                };
            })(names[i]);
        }
    })(Fn.prototype);
    module.exports = Fn;
});

define("ikj/svg/1.0.0/libs/reader-debug", [ "ikj/svg/1.0.0/libs/control-debug", "ikj/svg/1.0.0/element-debug", "arale/class/1.0.0/class-debug" ], function(require, exports, module) {
    var Control = require("ikj/svg/1.0.0/libs/control-debug");
    var Fn = Control.extend({
        createParts: function(node, options) {
            //indicator,name,value
            var parts = this.parts = {};
            var size = options.size || 1, color = options.color || "green";
            var width = size * 30, height = width / 2, r = width / 6;
            parts.indicator = paper.rect(-width / 2, -height / 2, width, height, width / 3).attr({
                fill: color,
                stroke: "#333",
                "stroke-width": .5
            }).appendTo(node);
            parts.name = paper.text("---", 0, r, r * 2.4).appendTo(node);
            parts.value = paper.text("0.00", width, r / 2, r * 2).appendTo(node);
        },
        setColor: function(color) {
            this.parts.indicator.style("fill", color);
            return this;
        }
    });
    (function(proto) {
        var names = "Value Name".split(" ");
        for (var i = names.length; i--; ) {
            (function(name) {
                proto["set" + name] = function(value) {
                    this.parts[name.toLowerCase()].text(value);
                    return this;
                };
            })(names[i]);
        }
    })(Fn.prototype);
    module.exports = Fn;
});
