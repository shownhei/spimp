define("ikj/daterangepicker/1.2.0/daterangepicker-debug", [ "$-debug", "./moment-debug", "./daterangepicker-bs2-debug.css" ], function(require, exports, module) {
    var $ = require("$-debug"), moment = require("./moment-debug");
    require("./daterangepicker-bs2-debug.css");
    /**
* @version: 1.2
* @author: Dan Grossman http://www.dangrossman.info/
* @date: 2013-07-25
* @copyright: Copyright (c) 2012-2013 Dan Grossman. All rights reserved.
* @license: Licensed under Apache License v2.0. See http://www.apache.org/licenses/LICENSE-2.0
* @website: http://www.improvely.com/
*/
    !function($) {
        var DateRangePicker = function(element, options, cb) {
            var hasOptions = typeof options == "object";
            var localeObject;
            //option defaults
            this.startDate = moment().startOf("day");
            this.endDate = moment().startOf("day");
            this.minDate = false;
            this.maxDate = false;
            this.dateLimit = false;
            this.showDropdowns = false;
            this.showWeekNumbers = false;
            this.timePicker = false;
            this.timePickerIncrement = 30;
            this.timePicker12Hour = true;
            this.ranges = {};
            this.opens = "right";
            this.buttonClasses = [ "btn", "btn-small" ];
            this.applyClass = "btn-success";
            this.cancelClass = "btn-default";
            this.format = "YYYY-MM-DD";
            this.separator = " 至 ";
            this.locale = {
                applyLabel: "确定",
                cancelLabel: "取消",
                fromLabel: "从",
                toLabel: "到",
                weekLabel: "W",
                customRangeLabel: "Custom Range",
                daysOfWeek: [ "一", "二", "三", "四", "五", "六", "日" ],
                monthNames: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
                firstDay: 1
            };
            this.cb = function() {};
            // by default, the daterangepicker element is placed at the bottom of HTML body
            this.parentEl = "body";
            //element that triggered the date range picker
            this.element = $(element);
            if (this.element.hasClass("pull-right")) this.opens = "left";
            if (this.element.is("input")) {
                this.element.on({
                    click: $.proxy(this.show, this),
                    focus: $.proxy(this.show, this)
                });
            } else {
                this.element.on("click", $.proxy(this.show, this));
            }
            localeObject = this.locale;
            if (hasOptions) {
                if (typeof options.locale == "object") {
                    $.each(localeObject, function(property, value) {
                        localeObject[property] = options.locale[property] || value;
                    });
                }
                if (options.applyClass) {
                    this.applyClass = options.applyClass;
                }
                if (options.cancelClass) {
                    this.cancelClass = options.cancelClass;
                }
            }
            var DRPTemplate = '<div class="daterangepicker dropdown-menu">' + '<div class="calendar left"></div>' + '<div class="calendar right"></div>' + '<div class="ranges">' + '<div class="range_inputs">' + '<div class="daterangepicker_start_input" style="float: left">' + '<label for="daterangepicker_start">' + this.locale.fromLabel + "</label>" + '<input class="input-mini" type="text" name="daterangepicker_start" value="" disabled="disabled" />' + "</div>" + '<div class="daterangepicker_end_input" style="float: left; padding-left: 11px">' + '<label for="daterangepicker_end">' + this.locale.toLabel + "</label>" + '<input class="input-mini" type="text" name="daterangepicker_end" value="" disabled="disabled" />' + "</div>" + '<button class="' + this.applyClass + ' applyBtn" disabled="disabled">' + this.locale.applyLabel + "</button>&nbsp;" + '<button class="' + this.cancelClass + ' cancelBtn">' + this.locale.cancelLabel + "</button>" + "</div>" + "</div>" + "</div>";
            this.parentEl = hasOptions && options.parentEl && $(options.parentEl) || $(this.parentEl);
            //the date range picker
            this.container = $(DRPTemplate).appendTo(this.parentEl);
            if (hasOptions) {
                if (typeof options.format == "string") this.format = options.format;
                if (typeof options.separator == "string") this.separator = options.separator;
                if (typeof options.startDate == "string") this.startDate = moment(options.startDate, this.format);
                if (typeof options.endDate == "string") this.endDate = moment(options.endDate, this.format);
                if (typeof options.minDate == "string") this.minDate = moment(options.minDate, this.format);
                if (typeof options.maxDate == "string") this.maxDate = moment(options.maxDate, this.format);
                if (typeof options.startDate == "object") this.startDate = moment(options.startDate);
                if (typeof options.endDate == "object") this.endDate = moment(options.endDate);
                if (typeof options.minDate == "object") this.minDate = moment(options.minDate);
                if (typeof options.maxDate == "object") this.maxDate = moment(options.maxDate);
                if (typeof options.ranges == "object") {
                    for (var range in options.ranges) {
                        var start = moment(options.ranges[range][0]);
                        var end = moment(options.ranges[range][1]);
                        // If we have a min/max date set, bound this range
                        // to it, but only if it would otherwise fall
                        // outside of the min/max.
                        if (this.minDate && start.isBefore(this.minDate)) start = moment(this.minDate);
                        if (this.maxDate && end.isAfter(this.maxDate)) end = moment(this.maxDate);
                        // If the end of the range is before the minimum (if min is set) OR
                        // the start of the range is after the max (also if set) don't display this
                        // range option.
                        if (this.minDate && end.isBefore(this.minDate) || this.maxDate && start.isAfter(this.maxDate)) {
                            continue;
                        }
                        this.ranges[range] = [ start, end ];
                    }
                    var list = "<ul>";
                    for (var range in this.ranges) {
                        list += "<li>" + range + "</li>";
                    }
                    list += "<li>" + this.locale.customRangeLabel + "</li>";
                    list += "</ul>";
                    this.container.find(".ranges").prepend(list);
                }
                if (typeof options.dateLimit == "object") this.dateLimit = options.dateLimit;
                // update day names order to firstDay
                if (typeof options.locale == "object") {
                    if (typeof options.locale.firstDay == "number") {
                        this.locale.firstDay = options.locale.firstDay;
                        var iterator = options.locale.firstDay;
                        while (iterator > 0) {
                            this.locale.daysOfWeek.push(this.locale.daysOfWeek.shift());
                            iterator--;
                        }
                    }
                }
                if (typeof options.opens == "string") this.opens = options.opens;
                if (typeof options.showWeekNumbers == "boolean") {
                    this.showWeekNumbers = options.showWeekNumbers;
                }
                if (typeof options.buttonClasses == "string") {
                    this.buttonClasses = [ options.buttonClasses ];
                }
                if (typeof options.buttonClasses == "object") {
                    this.buttonClasses = options.buttonClasses;
                }
                if (typeof options.showDropdowns == "boolean") {
                    this.showDropdowns = options.showDropdowns;
                }
                if (typeof options.timePicker == "boolean") {
                    this.timePicker = options.timePicker;
                }
                if (typeof options.timePickerIncrement == "number") {
                    this.timePickerIncrement = options.timePickerIncrement;
                }
                if (typeof options.timePicker12Hour == "boolean") {
                    this.timePicker12Hour = options.timePicker12Hour;
                }
            }
            if (!this.timePicker) {
                this.startDate = this.startDate.startOf("day");
                this.endDate = this.endDate.startOf("day");
            }
            //apply CSS classes to buttons
            var c = this.container;
            $.each(this.buttonClasses, function(idx, val) {
                c.find("button").addClass(val);
            });
            if (this.opens == "right") {
                //swap calendar positions
                var left = this.container.find(".calendar.left");
                var right = this.container.find(".calendar.right");
                left.removeClass("left").addClass("right");
                right.removeClass("right").addClass("left");
            }
            if (typeof options == "undefined" || typeof options.ranges == "undefined") {
                this.container.find(".calendar").show();
                this.move();
            }
            if (typeof cb == "function") this.cb = cb;
            this.container.addClass("opens" + this.opens);
            //try parse date if in text input
            if (!hasOptions || typeof options.startDate == "undefined" && typeof options.endDate == "undefined") {
                if ($(this.element).is("input[type=text]")) {
                    var val = $(this.element).val();
                    var split = val.split(this.separator);
                    var start, end;
                    if (split.length == 2) {
                        start = moment(split[0], this.format);
                        end = moment(split[1], this.format);
                    }
                    if (start != null && end != null) {
                        this.startDate = start;
                        this.endDate = end;
                    }
                }
            }
            //state
            this.oldStartDate = this.startDate.clone();
            this.oldEndDate = this.endDate.clone();
            this.leftCalendar = {
                month: moment([ this.startDate.year(), this.startDate.month(), 1, this.startDate.hour(), this.startDate.minute() ]),
                calendar: []
            };
            this.rightCalendar = {
                month: moment([ this.endDate.year(), this.endDate.month(), 1, this.endDate.hour(), this.endDate.minute() ]),
                calendar: []
            };
            //event listeners
            this.container.on("mousedown", $.proxy(this.mousedown, this));
            this.container.find(".calendar").on("click", ".prev", $.proxy(this.clickPrev, this));
            this.container.find(".calendar").on("click", ".next", $.proxy(this.clickNext, this));
            this.container.find(".ranges").on("click", "button.applyBtn", $.proxy(this.clickApply, this));
            this.container.find(".ranges").on("click", "button.cancelBtn", $.proxy(this.clickCancel, this));
            this.container.find(".ranges").on("click", ".daterangepicker_start_input", $.proxy(this.showCalendars, this));
            this.container.find(".ranges").on("click", ".daterangepicker_end_input", $.proxy(this.showCalendars, this));
            this.container.find(".calendar").on("click", "td.available", $.proxy(this.clickDate, this));
            this.container.find(".calendar").on("mouseenter", "td.available", $.proxy(this.enterDate, this));
            this.container.find(".calendar").on("mouseleave", "td.available", $.proxy(this.updateView, this));
            this.container.find(".ranges").on("click", "li", $.proxy(this.clickRange, this));
            this.container.find(".ranges").on("mouseenter", "li", $.proxy(this.enterRange, this));
            this.container.find(".ranges").on("mouseleave", "li", $.proxy(this.updateView, this));
            this.container.find(".calendar").on("change", "select.yearselect", $.proxy(this.updateMonthYear, this));
            this.container.find(".calendar").on("change", "select.monthselect", $.proxy(this.updateMonthYear, this));
            this.container.find(".calendar").on("change", "select.hourselect", $.proxy(this.updateTime, this));
            this.container.find(".calendar").on("change", "select.minuteselect", $.proxy(this.updateTime, this));
            this.container.find(".calendar").on("change", "select.ampmselect", $.proxy(this.updateTime, this));
            this.element.on("keyup", $.proxy(this.updateFromControl, this));
            this.updateView();
            this.updateCalendars();
        };
        DateRangePicker.prototype = {
            constructor: DateRangePicker,
            mousedown: function(e) {
                e.stopPropagation();
            },
            updateView: function() {
                this.leftCalendar.month.month(this.startDate.month()).year(this.startDate.year());
                this.rightCalendar.month.month(this.endDate.month()).year(this.endDate.year());
                this.container.find("input[name=daterangepicker_start]").val(this.startDate.format(this.format));
                this.container.find("input[name=daterangepicker_end]").val(this.endDate.format(this.format));
                if (this.startDate.isSame(this.endDate) || this.startDate.isBefore(this.endDate)) {
                    this.container.find("button.applyBtn").removeAttr("disabled");
                } else {
                    this.container.find("button.applyBtn").attr("disabled", "disabled");
                }
            },
            updateFromControl: function() {
                if (!this.element.is("input")) return;
                if (!this.element.val().length) return;
                var dateString = this.element.val().split(this.separator);
                var start = moment(dateString[0], this.format);
                var end = moment(dateString[1], this.format);
                if (start == null || end == null) return;
                if (end.isBefore(start)) return;
                this.startDate = start;
                this.endDate = end;
                this.notify();
                this.updateCalendars();
            },
            notify: function() {
                this.updateView();
                this.cb(this.startDate, this.endDate);
            },
            move: function() {
                var parentOffset = {
                    top: this.parentEl.offset().top - (this.parentEl.is("body") ? 0 : this.parentEl.scrollTop()),
                    left: this.parentEl.offset().left - (this.parentEl.is("body") ? 0 : this.parentEl.scrollLeft())
                };
                if (this.opens == "left") {
                    this.container.css({
                        top: this.element.offset().top + this.element.outerHeight() - parentOffset.top,
                        right: $(window).width() - this.element.offset().left - this.element.outerWidth() - parentOffset.left,
                        left: "auto"
                    });
                    if (this.container.offset().left < 0) {
                        this.container.css({
                            right: "auto",
                            left: 9
                        });
                    }
                } else {
                    this.container.css({
                        top: this.element.offset().top + this.element.outerHeight() - parentOffset.top,
                        left: this.element.offset().left - parentOffset.left,
                        right: "auto"
                    });
                    if (this.container.offset().left + this.container.outerWidth() > $(window).width()) {
                        this.container.css({
                            left: "auto",
                            right: 0
                        });
                    }
                }
            },
            show: function(e) {
                this.container.show();
                this.move();
                if (e) {
                    e.stopPropagation();
                    e.preventDefault();
                }
                $(document).on("mousedown", $.proxy(this.hide, this));
                this.element.trigger("shown", {
                    target: e.target,
                    picker: this
                });
            },
            hide: function(e) {
                this.container.hide();
                if (!this.startDate.isSame(this.oldStartDate) || !this.endDate.isSame(this.oldEndDate)) this.notify();
                this.oldStartDate = this.startDate.clone();
                this.oldEndDate = this.endDate.clone();
                $(document).off("mousedown", this.hide);
                this.element.trigger("hidden", {
                    picker: this
                });
            },
            enterRange: function(e) {
                var label = e.target.innerHTML;
                if (label == this.locale.customRangeLabel) {
                    this.updateView();
                } else {
                    var dates = this.ranges[label];
                    this.container.find("input[name=daterangepicker_start]").val(dates[0].format(this.format));
                    this.container.find("input[name=daterangepicker_end]").val(dates[1].format(this.format));
                }
            },
            showCalendars: function() {
                this.container.find(".calendar").show();
                this.move();
            },
            updateInputText: function() {
                if (this.element.is("input")) this.element.val(this.startDate.format(this.format) + this.separator + this.endDate.format(this.format));
            },
            clickRange: function(e) {
                var label = e.target.innerHTML;
                if (label == this.locale.customRangeLabel) {
                    this.showCalendars();
                } else {
                    var dates = this.ranges[label];
                    this.startDate = dates[0];
                    this.endDate = dates[1];
                    if (!this.timePicker) {
                        this.startDate.startOf("day");
                        this.endDate.startOf("day");
                    }
                    this.leftCalendar.month.month(this.startDate.month()).year(this.startDate.year()).hour(this.startDate.hour()).minute(this.startDate.minute());
                    this.rightCalendar.month.month(this.endDate.month()).year(this.endDate.year()).hour(this.endDate.hour()).minute(this.endDate.minute());
                    this.updateCalendars();
                    this.updateInputText();
                    this.container.find(".calendar").hide();
                    this.hide();
                }
            },
            clickPrev: function(e) {
                var cal = $(e.target).parents(".calendar");
                if (cal.hasClass("left")) {
                    this.leftCalendar.month.subtract("month", 1);
                } else {
                    this.rightCalendar.month.subtract("month", 1);
                }
                this.updateCalendars();
            },
            clickNext: function(e) {
                var cal = $(e.target).parents(".calendar");
                if (cal.hasClass("left")) {
                    this.leftCalendar.month.add("month", 1);
                } else {
                    this.rightCalendar.month.add("month", 1);
                }
                this.updateCalendars();
            },
            enterDate: function(e) {
                var title = $(e.target).attr("data-title");
                var row = title.substr(1, 1);
                var col = title.substr(3, 1);
                var cal = $(e.target).parents(".calendar");
                if (cal.hasClass("left")) {
                    this.container.find("input[name=daterangepicker_start]").val(this.leftCalendar.calendar[row][col].format(this.format));
                } else {
                    this.container.find("input[name=daterangepicker_end]").val(this.rightCalendar.calendar[row][col].format(this.format));
                }
            },
            clickDate: function(e) {
                var title = $(e.target).attr("data-title");
                var row = title.substr(1, 1);
                var col = title.substr(3, 1);
                var cal = $(e.target).parents(".calendar");
                if (cal.hasClass("left")) {
                    var startDate = this.leftCalendar.calendar[row][col];
                    var endDate = this.endDate;
                    if (typeof this.dateLimit == "object") {
                        var maxDate = moment(startDate).add(this.dateLimit).startOf("day");
                        if (endDate.isAfter(maxDate)) {
                            endDate = maxDate;
                        }
                    }
                } else {
                    var startDate = this.startDate;
                    var endDate = this.rightCalendar.calendar[row][col];
                    if (typeof this.dateLimit == "object") {
                        var minDate = moment(endDate).subtract(this.dateLimit).startOf("day");
                        if (startDate.isBefore(minDate)) {
                            startDate = minDate;
                        }
                    }
                }
                cal.find("td").removeClass("active");
                if (startDate.isSame(endDate) || startDate.isBefore(endDate)) {
                    $(e.target).addClass("active");
                    this.startDate = startDate;
                    this.endDate = endDate;
                } else if (startDate.isAfter(endDate)) {
                    $(e.target).addClass("active");
                    this.startDate = startDate;
                    this.endDate = moment(startDate).add("day", 1).startOf("day");
                }
                this.leftCalendar.month.month(this.startDate.month()).year(this.startDate.year());
                this.rightCalendar.month.month(this.endDate.month()).year(this.endDate.year());
                this.updateCalendars();
            },
            clickApply: function(e) {
                this.updateInputText();
                this.hide();
            },
            clickCancel: function(e) {
                this.startDate = this.oldStartDate;
                this.endDate = this.oldEndDate;
                this.updateView();
                this.updateCalendars();
                this.hide();
            },
            updateMonthYear: function(e) {
                var isLeft = $(e.target).closest(".calendar").hasClass("left");
                var cal = this.container.find(".calendar.left");
                if (!isLeft) cal = this.container.find(".calendar.right");
                var month = parseInt(cal.find(".monthselect").val());
                var year = cal.find(".yearselect").val();
                if (isLeft) {
                    this.leftCalendar.month.month(month).year(year);
                } else {
                    this.rightCalendar.month.month(month).year(year);
                }
                this.updateCalendars();
            },
            updateTime: function(e) {
                var isLeft = $(e.target).closest(".calendar").hasClass("left");
                var cal = this.container.find(".calendar.left");
                if (!isLeft) cal = this.container.find(".calendar.right");
                var hour = parseInt(cal.find(".hourselect").val());
                var minute = parseInt(cal.find(".minuteselect").val());
                if (this.timePicker12Hour) {
                    var ampm = cal.find(".ampmselect").val();
                    if (ampm == "PM" && hour < 12) hour += 12;
                    if (ampm == "AM" && hour == 12) hour = 0;
                }
                if (isLeft) {
                    var start = this.startDate;
                    start.hour(hour);
                    start.minute(minute);
                    this.startDate = start;
                    this.leftCalendar.month.hour(hour).minute(minute);
                } else {
                    var end = this.endDate;
                    end.hour(hour);
                    end.minute(minute);
                    this.endDate = end;
                    this.rightCalendar.month.hour(hour).minute(minute);
                }
                this.updateCalendars();
            },
            updateCalendars: function() {
                this.leftCalendar.calendar = this.buildCalendar(this.leftCalendar.month.month(), this.leftCalendar.month.year(), this.leftCalendar.month.hour(), this.leftCalendar.month.minute(), "left");
                this.rightCalendar.calendar = this.buildCalendar(this.rightCalendar.month.month(), this.rightCalendar.month.year(), this.rightCalendar.month.hour(), this.rightCalendar.month.minute(), "right");
                this.container.find(".calendar.left").html(this.renderCalendar(this.leftCalendar.calendar, this.startDate, this.minDate, this.maxDate));
                this.container.find(".calendar.right").html(this.renderCalendar(this.rightCalendar.calendar, this.endDate, this.startDate, this.maxDate));
                this.container.find(".ranges li").removeClass("active");
                var customRange = true;
                var i = 0;
                for (var range in this.ranges) {
                    if (this.timePicker) {
                        if (this.startDate.isSame(this.ranges[range][0]) && this.endDate.isSame(this.ranges[range][1])) {
                            customRange = false;
                            this.container.find(".ranges li:eq(" + i + ")").addClass("active");
                        }
                    } else {
                        //ignore times when comparing dates if time picker is not enabled
                        if (this.startDate.format("YYYY-MM-DD") == this.ranges[range][0].format("YYYY-MM-DD") && this.endDate.format("YYYY-MM-DD") == this.ranges[range][1].format("YYYY-MM-DD")) {
                            customRange = false;
                            this.container.find(".ranges li:eq(" + i + ")").addClass("active");
                        }
                    }
                    i++;
                }
                if (customRange) this.container.find(".ranges li:last").addClass("active");
            },
            buildCalendar: function(month, year, hour, minute, side) {
                var firstDay = moment([ year, month, 1 ]);
                var lastMonth = moment(firstDay).subtract("month", 1).month();
                var lastYear = moment(firstDay).subtract("month", 1).year();
                var daysInLastMonth = moment([ lastYear, lastMonth ]).daysInMonth();
                var dayOfWeek = firstDay.day();
                //initialize a 6 rows x 7 columns array for the calendar
                var calendar = [];
                for (var i = 0; i < 6; i++) {
                    calendar[i] = [];
                }
                //populate the calendar with date objects
                var startDay = daysInLastMonth - dayOfWeek + this.locale.firstDay + 1;
                if (startDay > daysInLastMonth) startDay -= 7;
                if (dayOfWeek == this.locale.firstDay) startDay = daysInLastMonth - 6;
                var curDate = moment([ lastYear, lastMonth, startDay, hour, minute ]);
                for (var i = 0, col = 0, row = 0; i < 42; i++, col++, curDate = moment(curDate).add("day", 1)) {
                    if (i > 0 && col % 7 == 0) {
                        col = 0;
                        row++;
                    }
                    calendar[row][col] = curDate;
                }
                return calendar;
            },
            renderDropdowns: function(selected, minDate, maxDate) {
                var currentMonth = selected.month();
                var monthHtml = '<select class="monthselect">';
                var inMinYear = false;
                var inMaxYear = false;
                for (var m = 0; m < 12; m++) {
                    if ((!inMinYear || m >= minDate.month()) && (!inMaxYear || m <= maxDate.month())) {
                        monthHtml += "<option value='" + m + "'" + (m === currentMonth ? " selected='selected'" : "") + ">" + this.locale.monthNames[m] + "</option>";
                    }
                }
                monthHtml += "</select>";
                var currentYear = selected.year();
                var maxYear = maxDate && maxDate.year() || currentYear + 5;
                var minYear = minDate && minDate.year() || currentYear - 50;
                var yearHtml = '<select class="yearselect">';
                for (var y = minYear; y <= maxYear; y++) {
                    yearHtml += '<option value="' + y + '"' + (y === currentYear ? ' selected="selected"' : "") + ">" + y + "</option>";
                }
                yearHtml += "</select>";
                return monthHtml + yearHtml;
            },
            renderCalendar: function(calendar, selected, minDate, maxDate) {
                var html = '<div class="calendar-date">';
                html += '<table class="table-condensed">';
                html += "<thead>";
                html += "<tr>";
                // add empty cell for week number
                if (this.showWeekNumbers) html += "<th></th>";
                if (!minDate || minDate.isBefore(calendar[1][1])) {
                    html += '<th class="prev available"><i class="icon-arrow-left glyphicon glyphicon-arrow-left"></i></th>';
                } else {
                    html += "<th></th>";
                }
                var dateHtml = this.locale.monthNames[calendar[1][1].month()] + calendar[1][1].format(" YYYY");
                if (this.showDropdowns) {
                    dateHtml = this.renderDropdowns(calendar[1][1], minDate, maxDate);
                }
                html += '<th colspan="5" style="width: auto">' + dateHtml + "</th>";
                if (!maxDate || maxDate.isAfter(calendar[1][1])) {
                    html += '<th class="next available"><i class="icon-arrow-right glyphicon glyphicon-arrow-right"></i></th>';
                } else {
                    html += "<th></th>";
                }
                html += "</tr>";
                html += "<tr>";
                // add week number label
                if (this.showWeekNumbers) html += '<th class="week">' + this.locale.weekLabel + "</th>";
                $.each(this.locale.daysOfWeek, function(index, dayOfWeek) {
                    html += "<th>" + dayOfWeek + "</th>";
                });
                html += "</tr>";
                html += "</thead>";
                html += "<tbody>";
                for (var row = 0; row < 6; row++) {
                    html += "<tr>";
                    // add week number
                    if (this.showWeekNumbers) html += '<td class="week">' + calendar[row][0].week() + "</td>";
                    for (var col = 0; col < 7; col++) {
                        var cname = "available ";
                        cname += calendar[row][col].month() == calendar[1][1].month() ? "" : "off";
                        if (minDate && calendar[row][col].isBefore(minDate) || maxDate && calendar[row][col].isAfter(maxDate)) {
                            cname = " off disabled ";
                        } else if (calendar[row][col].format("YYYY-MM-DD") == selected.format("YYYY-MM-DD")) {
                            cname += " active ";
                            if (calendar[row][col].format("YYYY-MM-DD") == this.startDate.format("YYYY-MM-DD")) {
                                cname += " start-date ";
                            }
                            if (calendar[row][col].format("YYYY-MM-DD") == this.endDate.format("YYYY-MM-DD")) {
                                cname += " end-date ";
                            }
                        } else if (calendar[row][col] >= this.startDate && calendar[row][col] <= this.endDate) {
                            cname += " in-range ";
                            if (calendar[row][col].isSame(this.startDate)) {
                                cname += " start-date ";
                            }
                            if (calendar[row][col].isSame(this.endDate)) {
                                cname += " end-date ";
                            }
                        }
                        var title = "r" + row + "c" + col;
                        html += '<td class="' + cname.replace(/\s+/g, " ").replace(/^\s?(.*?)\s?$/, "$1") + '" data-title="' + title + '">' + calendar[row][col].date() + "</td>";
                    }
                    html += "</tr>";
                }
                html += "</tbody>";
                html += "</table>";
                html += "</div>";
                if (this.timePicker) {
                    html += '<div class="calendar-time">';
                    html += '<select class="hourselect">';
                    var start = 0;
                    var end = 23;
                    var selected_hour = selected.hour();
                    if (this.timePicker12Hour) {
                        start = 1;
                        end = 12;
                        if (selected_hour >= 12) selected_hour -= 12;
                        if (selected_hour == 0) selected_hour = 12;
                    }
                    for (var i = start; i <= end; i++) {
                        if (i == selected_hour) {
                            html += '<option value="' + i + '" selected="selected">' + i + "</option>";
                        } else {
                            html += '<option value="' + i + '">' + i + "</option>";
                        }
                    }
                    html += "</select> : ";
                    html += '<select class="minuteselect">';
                    for (var i = 0; i < 60; i += this.timePickerIncrement) {
                        var num = i;
                        if (num < 10) num = "0" + num;
                        if (i == selected.minute()) {
                            html += '<option value="' + i + '" selected="selected">' + num + "</option>";
                        } else {
                            html += '<option value="' + i + '">' + num + "</option>";
                        }
                    }
                    html += "</select> ";
                    if (this.timePicker12Hour) {
                        html += '<select class="ampmselect">';
                        if (selected.hour() >= 12) {
                            html += '<option value="AM">AM</option><option value="PM" selected="selected">PM</option>';
                        } else {
                            html += '<option value="AM" selected="selected">AM</option><option value="PM">PM</option>';
                        }
                        html += "</select>";
                    }
                    html += "</div>";
                }
                return html;
            }
        };
        $.fn.daterangepicker = function(options, cb) {
            this.each(function() {
                var el = $(this);
                if (!el.data("daterangepicker")) el.data("daterangepicker", new DateRangePicker(el, options, cb));
            });
            return this;
        };
    }($);
});

define("ikj/daterangepicker/1.2.0/moment-debug", [], function(require, exports, module) {
    // moment.js
    // version : 2.1.0
    // author : Tim Wood
    // license : MIT
    // momentjs.com
    (function(undefined) {
        /************************************
        Constants
    ************************************/
        var moment, VERSION = "2.1.0", round = Math.round, i, // internal storage for language config files
        languages = {}, // check for nodeJS
        hasModule = typeof module !== "undefined" && module.exports, // ASP.NET json date format regex
        aspNetJsonRegex = /^\/?Date\((\-?\d+)/i, aspNetTimeSpanJsonRegex = /(\-)?(\d*)?\.?(\d+)\:(\d+)\:(\d+)\.?(\d{3})?/, // format tokens
        formattingTokens = /(\[[^\[]*\])|(\\)?(Mo|MM?M?M?|Do|DDDo|DD?D?D?|ddd?d?|do?|w[o|w]?|W[o|W]?|YYYYY|YYYY|YY|gg(ggg?)?|GG(GGG?)?|e|E|a|A|hh?|HH?|mm?|ss?|SS?S?|X|zz?|ZZ?|.)/g, localFormattingTokens = /(\[[^\[]*\])|(\\)?(LT|LL?L?L?|l{1,4})/g, // parsing token regexes
        parseTokenOneOrTwoDigits = /\d\d?/, // 0 - 99
        parseTokenOneToThreeDigits = /\d{1,3}/, // 0 - 999
        parseTokenThreeDigits = /\d{3}/, // 000 - 999
        parseTokenFourDigits = /\d{1,4}/, // 0 - 9999
        parseTokenSixDigits = /[+\-]?\d{1,6}/, // -999,999 - 999,999
        parseTokenWord = /[0-9]*['a-z\u00A0-\u05FF\u0700-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+|[\u0600-\u06FF\/]+(\s*?[\u0600-\u06FF]+){1,2}/i, // any word (or two) characters or numbers including two/three word month in arabic.
        parseTokenTimezone = /Z|[\+\-]\d\d:?\d\d/i, // +00:00 -00:00 +0000 -0000 or Z
        parseTokenT = /T/i, // T (ISO seperator)
        parseTokenTimestampMs = /[\+\-]?\d+(\.\d{1,3})?/, // 123456789 123456789.123
        // preliminary iso regex
        // 0000-00-00 + T + 00 or 00:00 or 00:00:00 or 00:00:00.000 + +00:00 or +0000
        isoRegex = /^\s*\d{4}-\d\d-\d\d((T| )(\d\d(:\d\d(:\d\d(\.\d\d?\d?)?)?)?)?([\+\-]\d\d:?\d\d)?)?/, isoFormat = "YYYY-MM-DDTHH:mm:ssZ", // iso time formats and regexes
        isoTimes = [ [ "HH:mm:ss.S", /(T| )\d\d:\d\d:\d\d\.\d{1,3}/ ], [ "HH:mm:ss", /(T| )\d\d:\d\d:\d\d/ ], [ "HH:mm", /(T| )\d\d:\d\d/ ], [ "HH", /(T| )\d\d/ ] ], // timezone chunker "+10:00" > ["10", "00"] or "-1530" > ["-15", "30"]
        parseTimezoneChunker = /([\+\-]|\d\d)/gi, // getter and setter names
        proxyGettersAndSetters = "Date|Hours|Minutes|Seconds|Milliseconds".split("|"), unitMillisecondFactors = {
            Milliseconds: 1,
            Seconds: 1e3,
            Minutes: 6e4,
            Hours: 36e5,
            Days: 864e5,
            Months: 2592e6,
            Years: 31536e6
        }, unitAliases = {
            ms: "millisecond",
            s: "second",
            m: "minute",
            h: "hour",
            d: "day",
            w: "week",
            M: "month",
            y: "year"
        }, // format function strings
        formatFunctions = {}, // tokens to ordinalize and pad
        ordinalizeTokens = "DDD w W M D d".split(" "), paddedTokens = "M D H h m s w W".split(" "), formatTokenFunctions = {
            M: function() {
                return this.month() + 1;
            },
            MMM: function(format) {
                return this.lang().monthsShort(this, format);
            },
            MMMM: function(format) {
                return this.lang().months(this, format);
            },
            D: function() {
                return this.date();
            },
            DDD: function() {
                return this.dayOfYear();
            },
            d: function() {
                return this.day();
            },
            dd: function(format) {
                return this.lang().weekdaysMin(this, format);
            },
            ddd: function(format) {
                return this.lang().weekdaysShort(this, format);
            },
            dddd: function(format) {
                return this.lang().weekdays(this, format);
            },
            w: function() {
                return this.week();
            },
            W: function() {
                return this.isoWeek();
            },
            YY: function() {
                return leftZeroFill(this.year() % 100, 2);
            },
            YYYY: function() {
                return leftZeroFill(this.year(), 4);
            },
            YYYYY: function() {
                return leftZeroFill(this.year(), 5);
            },
            gg: function() {
                return leftZeroFill(this.weekYear() % 100, 2);
            },
            gggg: function() {
                return this.weekYear();
            },
            ggggg: function() {
                return leftZeroFill(this.weekYear(), 5);
            },
            GG: function() {
                return leftZeroFill(this.isoWeekYear() % 100, 2);
            },
            GGGG: function() {
                return this.isoWeekYear();
            },
            GGGGG: function() {
                return leftZeroFill(this.isoWeekYear(), 5);
            },
            e: function() {
                return this.weekday();
            },
            E: function() {
                return this.isoWeekday();
            },
            a: function() {
                return this.lang().meridiem(this.hours(), this.minutes(), true);
            },
            A: function() {
                return this.lang().meridiem(this.hours(), this.minutes(), false);
            },
            H: function() {
                return this.hours();
            },
            h: function() {
                return this.hours() % 12 || 12;
            },
            m: function() {
                return this.minutes();
            },
            s: function() {
                return this.seconds();
            },
            S: function() {
                return ~~(this.milliseconds() / 100);
            },
            SS: function() {
                return leftZeroFill(~~(this.milliseconds() / 10), 2);
            },
            SSS: function() {
                return leftZeroFill(this.milliseconds(), 3);
            },
            Z: function() {
                var a = -this.zone(), b = "+";
                if (a < 0) {
                    a = -a;
                    b = "-";
                }
                return b + leftZeroFill(~~(a / 60), 2) + ":" + leftZeroFill(~~a % 60, 2);
            },
            ZZ: function() {
                var a = -this.zone(), b = "+";
                if (a < 0) {
                    a = -a;
                    b = "-";
                }
                return b + leftZeroFill(~~(10 * a / 6), 4);
            },
            z: function() {
                return this.zoneAbbr();
            },
            zz: function() {
                return this.zoneName();
            },
            X: function() {
                return this.unix();
            }
        };
        function padToken(func, count) {
            return function(a) {
                return leftZeroFill(func.call(this, a), count);
            };
        }
        function ordinalizeToken(func, period) {
            return function(a) {
                return this.lang().ordinal(func.call(this, a), period);
            };
        }
        while (ordinalizeTokens.length) {
            i = ordinalizeTokens.pop();
            formatTokenFunctions[i + "o"] = ordinalizeToken(formatTokenFunctions[i], i);
        }
        while (paddedTokens.length) {
            i = paddedTokens.pop();
            formatTokenFunctions[i + i] = padToken(formatTokenFunctions[i], 2);
        }
        formatTokenFunctions.DDDD = padToken(formatTokenFunctions.DDD, 3);
        /************************************
        Constructors
    ************************************/
        function Language() {}
        // Moment prototype object
        function Moment(config) {
            extend(this, config);
        }
        // Duration Constructor
        function Duration(duration) {
            var years = duration.years || duration.year || duration.y || 0, months = duration.months || duration.month || duration.M || 0, weeks = duration.weeks || duration.week || duration.w || 0, days = duration.days || duration.day || duration.d || 0, hours = duration.hours || duration.hour || duration.h || 0, minutes = duration.minutes || duration.minute || duration.m || 0, seconds = duration.seconds || duration.second || duration.s || 0, milliseconds = duration.milliseconds || duration.millisecond || duration.ms || 0;
            // store reference to input for deterministic cloning
            this._input = duration;
            // representation for dateAddRemove
            this._milliseconds = milliseconds + seconds * 1e3 + // 1000
            minutes * 6e4 + // 1000 * 60
            hours * 36e5;
            // 1000 * 60 * 60
            // Because of dateAddRemove treats 24 hours as different from a
            // day when working around DST, we need to store them separately
            this._days = days + weeks * 7;
            // It is impossible translate months into days without knowing
            // which months you are are talking about, so we have to store
            // it separately.
            this._months = months + years * 12;
            this._data = {};
            this._bubble();
        }
        /************************************
        Helpers
    ************************************/
        function extend(a, b) {
            for (var i in b) {
                if (b.hasOwnProperty(i)) {
                    a[i] = b[i];
                }
            }
            return a;
        }
        function absRound(number) {
            if (number < 0) {
                return Math.ceil(number);
            } else {
                return Math.floor(number);
            }
        }
        // left zero fill a number
        // see http://jsperf.com/left-zero-filling for performance comparison
        function leftZeroFill(number, targetLength) {
            var output = number + "";
            while (output.length < targetLength) {
                output = "0" + output;
            }
            return output;
        }
        // helper function for _.addTime and _.subtractTime
        function addOrSubtractDurationFromMoment(mom, duration, isAdding, ignoreUpdateOffset) {
            var milliseconds = duration._milliseconds, days = duration._days, months = duration._months, minutes, hours, currentDate;
            if (milliseconds) {
                mom._d.setTime(+mom._d + milliseconds * isAdding);
            }
            // store the minutes and hours so we can restore them
            if (days || months) {
                minutes = mom.minute();
                hours = mom.hour();
            }
            if (days) {
                mom.date(mom.date() + days * isAdding);
            }
            if (months) {
                mom.month(mom.month() + months * isAdding);
            }
            if (milliseconds && !ignoreUpdateOffset) {
                moment.updateOffset(mom);
            }
            // restore the minutes and hours after possibly changing dst
            if (days || months) {
                mom.minute(minutes);
                mom.hour(hours);
            }
        }
        // check if is an array
        function isArray(input) {
            return Object.prototype.toString.call(input) === "[object Array]";
        }
        // compare two arrays, return the number of differences
        function compareArrays(array1, array2) {
            var len = Math.min(array1.length, array2.length), lengthDiff = Math.abs(array1.length - array2.length), diffs = 0, i;
            for (i = 0; i < len; i++) {
                if (~~array1[i] !== ~~array2[i]) {
                    diffs++;
                }
            }
            return diffs + lengthDiff;
        }
        function normalizeUnits(units) {
            return units ? unitAliases[units] || units.toLowerCase().replace(/(.)s$/, "$1") : units;
        }
        /************************************
        Languages
    ************************************/
        Language.prototype = {
            set: function(config) {
                var prop, i;
                for (i in config) {
                    prop = config[i];
                    if (typeof prop === "function") {
                        this[i] = prop;
                    } else {
                        this["_" + i] = prop;
                    }
                }
            },
            _months: "January_February_March_April_May_June_July_August_September_October_November_December".split("_"),
            months: function(m) {
                return this._months[m.month()];
            },
            _monthsShort: "Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"),
            monthsShort: function(m) {
                return this._monthsShort[m.month()];
            },
            monthsParse: function(monthName) {
                var i, mom, regex;
                if (!this._monthsParse) {
                    this._monthsParse = [];
                }
                for (i = 0; i < 12; i++) {
                    // make the regex if we don't have it already
                    if (!this._monthsParse[i]) {
                        mom = moment([ 2e3, i ]);
                        regex = "^" + this.months(mom, "") + "|^" + this.monthsShort(mom, "");
                        this._monthsParse[i] = new RegExp(regex.replace(".", ""), "i");
                    }
                    // test the regex
                    if (this._monthsParse[i].test(monthName)) {
                        return i;
                    }
                }
            },
            _weekdays: "Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"),
            weekdays: function(m) {
                return this._weekdays[m.day()];
            },
            _weekdaysShort: "Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"),
            weekdaysShort: function(m) {
                return this._weekdaysShort[m.day()];
            },
            _weekdaysMin: "Su_Mo_Tu_We_Th_Fr_Sa".split("_"),
            weekdaysMin: function(m) {
                return this._weekdaysMin[m.day()];
            },
            weekdaysParse: function(weekdayName) {
                var i, mom, regex;
                if (!this._weekdaysParse) {
                    this._weekdaysParse = [];
                }
                for (i = 0; i < 7; i++) {
                    // make the regex if we don't have it already
                    if (!this._weekdaysParse[i]) {
                        mom = moment([ 2e3, 1 ]).day(i);
                        regex = "^" + this.weekdays(mom, "") + "|^" + this.weekdaysShort(mom, "") + "|^" + this.weekdaysMin(mom, "");
                        this._weekdaysParse[i] = new RegExp(regex.replace(".", ""), "i");
                    }
                    // test the regex
                    if (this._weekdaysParse[i].test(weekdayName)) {
                        return i;
                    }
                }
            },
            _longDateFormat: {
                LT: "h:mm A",
                L: "MM/DD/YYYY",
                LL: "MMMM D YYYY",
                LLL: "MMMM D YYYY LT",
                LLLL: "dddd, MMMM D YYYY LT"
            },
            longDateFormat: function(key) {
                var output = this._longDateFormat[key];
                if (!output && this._longDateFormat[key.toUpperCase()]) {
                    output = this._longDateFormat[key.toUpperCase()].replace(/MMMM|MM|DD|dddd/g, function(val) {
                        return val.slice(1);
                    });
                    this._longDateFormat[key] = output;
                }
                return output;
            },
            isPM: function(input) {
                return (input + "").toLowerCase()[0] === "p";
            },
            _meridiemParse: /[ap]\.?m?\.?/i,
            meridiem: function(hours, minutes, isLower) {
                if (hours > 11) {
                    return isLower ? "pm" : "PM";
                } else {
                    return isLower ? "am" : "AM";
                }
            },
            _calendar: {
                sameDay: "[Today at] LT",
                nextDay: "[Tomorrow at] LT",
                nextWeek: "dddd [at] LT",
                lastDay: "[Yesterday at] LT",
                lastWeek: "[Last] dddd [at] LT",
                sameElse: "L"
            },
            calendar: function(key, mom) {
                var output = this._calendar[key];
                return typeof output === "function" ? output.apply(mom) : output;
            },
            _relativeTime: {
                future: "in %s",
                past: "%s ago",
                s: "a few seconds",
                m: "a minute",
                mm: "%d minutes",
                h: "an hour",
                hh: "%d hours",
                d: "a day",
                dd: "%d days",
                M: "a month",
                MM: "%d months",
                y: "a year",
                yy: "%d years"
            },
            relativeTime: function(number, withoutSuffix, string, isFuture) {
                var output = this._relativeTime[string];
                return typeof output === "function" ? output(number, withoutSuffix, string, isFuture) : output.replace(/%d/i, number);
            },
            pastFuture: function(diff, output) {
                var format = this._relativeTime[diff > 0 ? "future" : "past"];
                return typeof format === "function" ? format(output) : format.replace(/%s/i, output);
            },
            ordinal: function(number) {
                return this._ordinal.replace("%d", number);
            },
            _ordinal: "%d",
            preparse: function(string) {
                return string;
            },
            postformat: function(string) {
                return string;
            },
            week: function(mom) {
                return weekOfYear(mom, this._week.dow, this._week.doy).week;
            },
            _week: {
                dow: 0,
                // Sunday is the first day of the week.
                doy: 6
            }
        };
        // Loads a language definition into the `languages` cache.  The function
        // takes a key and optionally values.  If not in the browser and no values
        // are provided, it will load the language file module.  As a convenience,
        // this function also returns the language values.
        function loadLang(key, values) {
            values.abbr = key;
            if (!languages[key]) {
                languages[key] = new Language();
            }
            languages[key].set(values);
            return languages[key];
        }
        // Determines which language definition to use and returns it.
        //
        // With no parameters, it will return the global language.  If you
        // pass in a language key, such as 'en', it will return the
        // definition for 'en', so long as 'en' has already been loaded using
        // moment.lang.
        function getLangDefinition(key) {
            if (!key) {
                return moment.fn._lang;
            }
            if (!languages[key] && hasModule) {
                try {
                    require("./lang/" + key);
                } catch (e) {
                    // call with no params to set to default
                    return moment.fn._lang;
                }
            }
            return languages[key];
        }
        /************************************
        Formatting
    ************************************/
        function removeFormattingTokens(input) {
            if (input.match(/\[.*\]/)) {
                return input.replace(/^\[|\]$/g, "");
            }
            return input.replace(/\\/g, "");
        }
        function makeFormatFunction(format) {
            var array = format.match(formattingTokens), i, length;
            for (i = 0, length = array.length; i < length; i++) {
                if (formatTokenFunctions[array[i]]) {
                    array[i] = formatTokenFunctions[array[i]];
                } else {
                    array[i] = removeFormattingTokens(array[i]);
                }
            }
            return function(mom) {
                var output = "";
                for (i = 0; i < length; i++) {
                    output += array[i] instanceof Function ? array[i].call(mom, format) : array[i];
                }
                return output;
            };
        }
        // format date using native date object
        function formatMoment(m, format) {
            var i = 5;
            function replaceLongDateFormatTokens(input) {
                return m.lang().longDateFormat(input) || input;
            }
            while (i-- && localFormattingTokens.test(format)) {
                format = format.replace(localFormattingTokens, replaceLongDateFormatTokens);
            }
            if (!formatFunctions[format]) {
                formatFunctions[format] = makeFormatFunction(format);
            }
            return formatFunctions[format](m);
        }
        /************************************
        Parsing
    ************************************/
        // get the regex to find the next token
        function getParseRegexForToken(token, config) {
            switch (token) {
              case "DDDD":
                return parseTokenThreeDigits;

              case "YYYY":
                return parseTokenFourDigits;

              case "YYYYY":
                return parseTokenSixDigits;

              case "S":
              case "SS":
              case "SSS":
              case "DDD":
                return parseTokenOneToThreeDigits;

              case "MMM":
              case "MMMM":
              case "dd":
              case "ddd":
              case "dddd":
                return parseTokenWord;

              case "a":
              case "A":
                return getLangDefinition(config._l)._meridiemParse;

              case "X":
                return parseTokenTimestampMs;

              case "Z":
              case "ZZ":
                return parseTokenTimezone;

              case "T":
                return parseTokenT;

              case "MM":
              case "DD":
              case "YY":
              case "HH":
              case "hh":
              case "mm":
              case "ss":
              case "M":
              case "D":
              case "d":
              case "H":
              case "h":
              case "m":
              case "s":
                return parseTokenOneOrTwoDigits;

              default:
                return new RegExp(token.replace("\\", ""));
            }
        }
        function timezoneMinutesFromString(string) {
            var tzchunk = (parseTokenTimezone.exec(string) || [])[0], parts = (tzchunk + "").match(parseTimezoneChunker) || [ "-", 0, 0 ], minutes = +(parts[1] * 60) + ~~parts[2];
            return parts[0] === "+" ? -minutes : minutes;
        }
        // function to convert string input to date
        function addTimeToArrayFromToken(token, input, config) {
            var a, datePartArray = config._a;
            switch (token) {
              // MONTH
                case "M":
              // fall through to MM
                case "MM":
                datePartArray[1] = input == null ? 0 : ~~input - 1;
                break;

              case "MMM":
              // fall through to MMMM
                case "MMMM":
                a = getLangDefinition(config._l).monthsParse(input);
                // if we didn't find a month name, mark the date as invalid.
                if (a != null) {
                    datePartArray[1] = a;
                } else {
                    config._isValid = false;
                }
                break;

              // DAY OF MONTH
                case "D":
              // fall through to DDDD
                case "DD":
              // fall through to DDDD
                case "DDD":
              // fall through to DDDD
                case "DDDD":
                if (input != null) {
                    datePartArray[2] = ~~input;
                }
                break;

              // YEAR
                case "YY":
                datePartArray[0] = ~~input + (~~input > 68 ? 1900 : 2e3);
                break;

              case "YYYY":
              case "YYYYY":
                datePartArray[0] = ~~input;
                break;

              // AM / PM
                case "a":
              // fall through to A
                case "A":
                config._isPm = getLangDefinition(config._l).isPM(input);
                break;

              // 24 HOUR
                case "H":
              // fall through to hh
                case "HH":
              // fall through to hh
                case "h":
              // fall through to hh
                case "hh":
                datePartArray[3] = ~~input;
                break;

              // MINUTE
                case "m":
              // fall through to mm
                case "mm":
                datePartArray[4] = ~~input;
                break;

              // SECOND
                case "s":
              // fall through to ss
                case "ss":
                datePartArray[5] = ~~input;
                break;

              // MILLISECOND
                case "S":
              case "SS":
              case "SSS":
                datePartArray[6] = ~~(("0." + input) * 1e3);
                break;

              // UNIX TIMESTAMP WITH MS
                case "X":
                config._d = new Date(parseFloat(input) * 1e3);
                break;

              // TIMEZONE
                case "Z":
              // fall through to ZZ
                case "ZZ":
                config._useUTC = true;
                config._tzm = timezoneMinutesFromString(input);
                break;
            }
            // if the input is null, the date is not valid
            if (input == null) {
                config._isValid = false;
            }
        }
        // convert an array to a date.
        // the array should mirror the parameters below
        // note: all values past the year are optional and will default to the lowest possible value.
        // [year, month, day , hour, minute, second, millisecond]
        function dateFromArray(config) {
            var i, date, input = [];
            if (config._d) {
                return;
            }
            for (i = 0; i < 7; i++) {
                config._a[i] = input[i] = config._a[i] == null ? i === 2 ? 1 : 0 : config._a[i];
            }
            // add the offsets to the time to be parsed so that we can have a clean array for checking isValid
            input[3] += ~~((config._tzm || 0) / 60);
            input[4] += ~~((config._tzm || 0) % 60);
            date = new Date(0);
            if (config._useUTC) {
                date.setUTCFullYear(input[0], input[1], input[2]);
                date.setUTCHours(input[3], input[4], input[5], input[6]);
            } else {
                date.setFullYear(input[0], input[1], input[2]);
                date.setHours(input[3], input[4], input[5], input[6]);
            }
            config._d = date;
        }
        // date from string and format string
        function makeDateFromStringAndFormat(config) {
            // This array is used to make a Date, either with `new Date` or `Date.UTC`
            var tokens = config._f.match(formattingTokens), string = config._i, i, parsedInput;
            config._a = [];
            for (i = 0; i < tokens.length; i++) {
                parsedInput = (getParseRegexForToken(tokens[i], config).exec(string) || [])[0];
                if (parsedInput) {
                    string = string.slice(string.indexOf(parsedInput) + parsedInput.length);
                }
                // don't parse if its not a known token
                if (formatTokenFunctions[tokens[i]]) {
                    addTimeToArrayFromToken(tokens[i], parsedInput, config);
                }
            }
            // add remaining unparsed input to the string
            if (string) {
                config._il = string;
            }
            // handle am pm
            if (config._isPm && config._a[3] < 12) {
                config._a[3] += 12;
            }
            // if is 12 am, change hours to 0
            if (config._isPm === false && config._a[3] === 12) {
                config._a[3] = 0;
            }
            // return
            dateFromArray(config);
        }
        // date from string and array of format strings
        function makeDateFromStringAndArray(config) {
            var tempConfig, tempMoment, bestMoment, scoreToBeat = 99, i, currentScore;
            for (i = 0; i < config._f.length; i++) {
                tempConfig = extend({}, config);
                tempConfig._f = config._f[i];
                makeDateFromStringAndFormat(tempConfig);
                tempMoment = new Moment(tempConfig);
                currentScore = compareArrays(tempConfig._a, tempMoment.toArray());
                // if there is any input that was not parsed
                // add a penalty for that format
                if (tempMoment._il) {
                    currentScore += tempMoment._il.length;
                }
                if (currentScore < scoreToBeat) {
                    scoreToBeat = currentScore;
                    bestMoment = tempMoment;
                }
            }
            extend(config, bestMoment);
        }
        // date from iso format
        function makeDateFromString(config) {
            var i, string = config._i, match = isoRegex.exec(string);
            if (match) {
                // match[2] should be "T" or undefined
                config._f = "YYYY-MM-DD" + (match[2] || " ");
                for (i = 0; i < 4; i++) {
                    if (isoTimes[i][1].exec(string)) {
                        config._f += isoTimes[i][0];
                        break;
                    }
                }
                if (parseTokenTimezone.exec(string)) {
                    config._f += " Z";
                }
                makeDateFromStringAndFormat(config);
            } else {
                config._d = new Date(string);
            }
        }
        function makeDateFromInput(config) {
            var input = config._i, matched = aspNetJsonRegex.exec(input);
            if (input === undefined) {
                config._d = new Date();
            } else if (matched) {
                config._d = new Date(+matched[1]);
            } else if (typeof input === "string") {
                makeDateFromString(config);
            } else if (isArray(input)) {
                config._a = input.slice(0);
                dateFromArray(config);
            } else {
                config._d = input instanceof Date ? new Date(+input) : new Date(input);
            }
        }
        /************************************
        Relative Time
    ************************************/
        // helper function for moment.fn.from, moment.fn.fromNow, and moment.duration.fn.humanize
        function substituteTimeAgo(string, number, withoutSuffix, isFuture, lang) {
            return lang.relativeTime(number || 1, !!withoutSuffix, string, isFuture);
        }
        function relativeTime(milliseconds, withoutSuffix, lang) {
            var seconds = round(Math.abs(milliseconds) / 1e3), minutes = round(seconds / 60), hours = round(minutes / 60), days = round(hours / 24), years = round(days / 365), args = seconds < 45 && [ "s", seconds ] || minutes === 1 && [ "m" ] || minutes < 45 && [ "mm", minutes ] || hours === 1 && [ "h" ] || hours < 22 && [ "hh", hours ] || days === 1 && [ "d" ] || days <= 25 && [ "dd", days ] || days <= 45 && [ "M" ] || days < 345 && [ "MM", round(days / 30) ] || years === 1 && [ "y" ] || [ "yy", years ];
            args[2] = withoutSuffix;
            args[3] = milliseconds > 0;
            args[4] = lang;
            return substituteTimeAgo.apply({}, args);
        }
        /************************************
        Week of Year
    ************************************/
        // firstDayOfWeek       0 = sun, 6 = sat
        //                      the day of the week that starts the week
        //                      (usually sunday or monday)
        // firstDayOfWeekOfYear 0 = sun, 6 = sat
        //                      the first week is the week that contains the first
        //                      of this day of the week
        //                      (eg. ISO weeks use thursday (4))
        function weekOfYear(mom, firstDayOfWeek, firstDayOfWeekOfYear) {
            var end = firstDayOfWeekOfYear - firstDayOfWeek, daysToDayOfWeek = firstDayOfWeekOfYear - mom.day(), adjustedMoment;
            if (daysToDayOfWeek > end) {
                daysToDayOfWeek -= 7;
            }
            if (daysToDayOfWeek < end - 7) {
                daysToDayOfWeek += 7;
            }
            adjustedMoment = moment(mom).add("d", daysToDayOfWeek);
            return {
                week: Math.ceil(adjustedMoment.dayOfYear() / 7),
                year: adjustedMoment.year()
            };
        }
        /************************************
        Top Level Functions
    ************************************/
        function makeMoment(config) {
            var input = config._i, format = config._f;
            if (input === null || input === "") {
                return null;
            }
            if (typeof input === "string") {
                config._i = input = getLangDefinition().preparse(input);
            }
            if (moment.isMoment(input)) {
                config = extend({}, input);
                config._d = new Date(+input._d);
            } else if (format) {
                if (isArray(format)) {
                    makeDateFromStringAndArray(config);
                } else {
                    makeDateFromStringAndFormat(config);
                }
            } else {
                makeDateFromInput(config);
            }
            return new Moment(config);
        }
        moment = function(input, format, lang) {
            return makeMoment({
                _i: input,
                _f: format,
                _l: lang,
                _isUTC: false
            });
        };
        // creating with utc
        moment.utc = function(input, format, lang) {
            return makeMoment({
                _useUTC: true,
                _isUTC: true,
                _l: lang,
                _i: input,
                _f: format
            });
        };
        // creating with unix timestamp (in seconds)
        moment.unix = function(input) {
            return moment(input * 1e3);
        };
        // duration
        moment.duration = function(input, key) {
            var isDuration = moment.isDuration(input), isNumber = typeof input === "number", duration = isDuration ? input._input : isNumber ? {} : input, matched = aspNetTimeSpanJsonRegex.exec(input), sign, ret;
            if (isNumber) {
                if (key) {
                    duration[key] = input;
                } else {
                    duration.milliseconds = input;
                }
            } else if (matched) {
                sign = matched[1] === "-" ? -1 : 1;
                duration = {
                    y: 0,
                    d: ~~matched[2] * sign,
                    h: ~~matched[3] * sign,
                    m: ~~matched[4] * sign,
                    s: ~~matched[5] * sign,
                    ms: ~~matched[6] * sign
                };
            }
            ret = new Duration(duration);
            if (isDuration && input.hasOwnProperty("_lang")) {
                ret._lang = input._lang;
            }
            return ret;
        };
        // version number
        moment.version = VERSION;
        // default format
        moment.defaultFormat = isoFormat;
        // This function will be called whenever a moment is mutated.
        // It is intended to keep the offset in sync with the timezone.
        moment.updateOffset = function() {};
        // This function will load languages and then set the global language.  If
        // no arguments are passed in, it will simply return the current global
        // language key.
        moment.lang = function(key, values) {
            if (!key) {
                return moment.fn._lang._abbr;
            }
            if (values) {
                loadLang(key, values);
            } else if (!languages[key]) {
                getLangDefinition(key);
            }
            moment.duration.fn._lang = moment.fn._lang = getLangDefinition(key);
        };
        // returns language data
        moment.langData = function(key) {
            if (key && key._lang && key._lang._abbr) {
                key = key._lang._abbr;
            }
            return getLangDefinition(key);
        };
        // compare moment object
        moment.isMoment = function(obj) {
            return obj instanceof Moment;
        };
        // for typechecking Duration objects
        moment.isDuration = function(obj) {
            return obj instanceof Duration;
        };
        /************************************
        Moment Prototype
    ************************************/
        moment.fn = Moment.prototype = {
            clone: function() {
                return moment(this);
            },
            valueOf: function() {
                return +this._d + (this._offset || 0) * 6e4;
            },
            unix: function() {
                return Math.floor(+this / 1e3);
            },
            toString: function() {
                return this.format("ddd MMM DD YYYY HH:mm:ss [GMT]ZZ");
            },
            toDate: function() {
                return this._offset ? new Date(+this) : this._d;
            },
            toISOString: function() {
                return formatMoment(moment(this).utc(), "YYYY-MM-DD[T]HH:mm:ss.SSS[Z]");
            },
            toArray: function() {
                var m = this;
                return [ m.year(), m.month(), m.date(), m.hours(), m.minutes(), m.seconds(), m.milliseconds() ];
            },
            isValid: function() {
                if (this._isValid == null) {
                    if (this._a) {
                        this._isValid = !compareArrays(this._a, (this._isUTC ? moment.utc(this._a) : moment(this._a)).toArray());
                    } else {
                        this._isValid = !isNaN(this._d.getTime());
                    }
                }
                return !!this._isValid;
            },
            utc: function() {
                return this.zone(0);
            },
            local: function() {
                this.zone(0);
                this._isUTC = false;
                return this;
            },
            format: function(inputString) {
                var output = formatMoment(this, inputString || moment.defaultFormat);
                return this.lang().postformat(output);
            },
            add: function(input, val) {
                var dur;
                // switch args to support add('s', 1) and add(1, 's')
                if (typeof input === "string") {
                    dur = moment.duration(+val, input);
                } else {
                    dur = moment.duration(input, val);
                }
                addOrSubtractDurationFromMoment(this, dur, 1);
                return this;
            },
            subtract: function(input, val) {
                var dur;
                // switch args to support subtract('s', 1) and subtract(1, 's')
                if (typeof input === "string") {
                    dur = moment.duration(+val, input);
                } else {
                    dur = moment.duration(input, val);
                }
                addOrSubtractDurationFromMoment(this, dur, -1);
                return this;
            },
            diff: function(input, units, asFloat) {
                var that = this._isUTC ? moment(input).zone(this._offset || 0) : moment(input).local(), zoneDiff = (this.zone() - that.zone()) * 6e4, diff, output;
                units = normalizeUnits(units);
                if (units === "year" || units === "month") {
                    // average number of days in the months in the given dates
                    diff = (this.daysInMonth() + that.daysInMonth()) * 432e5;
                    // 24 * 60 * 60 * 1000 / 2
                    // difference in months
                    output = (this.year() - that.year()) * 12 + (this.month() - that.month());
                    // adjust by taking difference in days, average number of days
                    // and dst in the given months.
                    output += (this - moment(this).startOf("month") - (that - moment(that).startOf("month"))) / diff;
                    // same as above but with zones, to negate all dst
                    output -= (this.zone() - moment(this).startOf("month").zone() - (that.zone() - moment(that).startOf("month").zone())) * 6e4 / diff;
                    if (units === "year") {
                        output = output / 12;
                    }
                } else {
                    diff = this - that;
                    output = units === "second" ? diff / 1e3 : // 1000
                    units === "minute" ? diff / 6e4 : // 1000 * 60
                    units === "hour" ? diff / 36e5 : // 1000 * 60 * 60
                    units === "day" ? (diff - zoneDiff) / 864e5 : // 1000 * 60 * 60 * 24, negate dst
                    units === "week" ? (diff - zoneDiff) / 6048e5 : // 1000 * 60 * 60 * 24 * 7, negate dst
                    diff;
                }
                return asFloat ? output : absRound(output);
            },
            from: function(time, withoutSuffix) {
                return moment.duration(this.diff(time)).lang(this.lang()._abbr).humanize(!withoutSuffix);
            },
            fromNow: function(withoutSuffix) {
                return this.from(moment(), withoutSuffix);
            },
            calendar: function() {
                var diff = this.diff(moment().startOf("day"), "days", true), format = diff < -6 ? "sameElse" : diff < -1 ? "lastWeek" : diff < 0 ? "lastDay" : diff < 1 ? "sameDay" : diff < 2 ? "nextDay" : diff < 7 ? "nextWeek" : "sameElse";
                return this.format(this.lang().calendar(format, this));
            },
            isLeapYear: function() {
                var year = this.year();
                return year % 4 === 0 && year % 100 !== 0 || year % 400 === 0;
            },
            isDST: function() {
                return this.zone() < this.clone().month(0).zone() || this.zone() < this.clone().month(5).zone();
            },
            day: function(input) {
                var day = this._isUTC ? this._d.getUTCDay() : this._d.getDay();
                if (input != null) {
                    if (typeof input === "string") {
                        input = this.lang().weekdaysParse(input);
                        if (typeof input !== "number") {
                            return this;
                        }
                    }
                    return this.add({
                        d: input - day
                    });
                } else {
                    return day;
                }
            },
            month: function(input) {
                var utc = this._isUTC ? "UTC" : "", dayOfMonth, daysInMonth;
                if (input != null) {
                    if (typeof input === "string") {
                        input = this.lang().monthsParse(input);
                        if (typeof input !== "number") {
                            return this;
                        }
                    }
                    dayOfMonth = this.date();
                    this.date(1);
                    this._d["set" + utc + "Month"](input);
                    this.date(Math.min(dayOfMonth, this.daysInMonth()));
                    moment.updateOffset(this);
                    return this;
                } else {
                    return this._d["get" + utc + "Month"]();
                }
            },
            startOf: function(units) {
                units = normalizeUnits(units);
                // the following switch intentionally omits break keywords
                // to utilize falling through the cases.
                switch (units) {
                  case "year":
                    this.month(0);

                  /* falls through */
                    case "month":
                    this.date(1);

                  /* falls through */
                    case "week":
                  case "day":
                    this.hours(0);

                  /* falls through */
                    case "hour":
                    this.minutes(0);

                  /* falls through */
                    case "minute":
                    this.seconds(0);

                  /* falls through */
                    case "second":
                    this.milliseconds(0);
                }
                // weeks are a special case
                if (units === "week") {
                    this.weekday(0);
                }
                return this;
            },
            endOf: function(units) {
                return this.startOf(units).add(units, 1).subtract("ms", 1);
            },
            isAfter: function(input, units) {
                units = typeof units !== "undefined" ? units : "millisecond";
                return +this.clone().startOf(units) > +moment(input).startOf(units);
            },
            isBefore: function(input, units) {
                units = typeof units !== "undefined" ? units : "millisecond";
                return +this.clone().startOf(units) < +moment(input).startOf(units);
            },
            isSame: function(input, units) {
                units = typeof units !== "undefined" ? units : "millisecond";
                return +this.clone().startOf(units) === +moment(input).startOf(units);
            },
            min: function(other) {
                other = moment.apply(null, arguments);
                return other < this ? this : other;
            },
            max: function(other) {
                other = moment.apply(null, arguments);
                return other > this ? this : other;
            },
            zone: function(input) {
                var offset = this._offset || 0;
                if (input != null) {
                    if (typeof input === "string") {
                        input = timezoneMinutesFromString(input);
                    }
                    if (Math.abs(input) < 16) {
                        input = input * 60;
                    }
                    this._offset = input;
                    this._isUTC = true;
                    if (offset !== input) {
                        addOrSubtractDurationFromMoment(this, moment.duration(offset - input, "m"), 1, true);
                    }
                } else {
                    return this._isUTC ? offset : this._d.getTimezoneOffset();
                }
                return this;
            },
            zoneAbbr: function() {
                return this._isUTC ? "UTC" : "";
            },
            zoneName: function() {
                return this._isUTC ? "Coordinated Universal Time" : "";
            },
            daysInMonth: function() {
                return moment.utc([ this.year(), this.month() + 1, 0 ]).date();
            },
            dayOfYear: function(input) {
                var dayOfYear = round((moment(this).startOf("day") - moment(this).startOf("year")) / 864e5) + 1;
                return input == null ? dayOfYear : this.add("d", input - dayOfYear);
            },
            weekYear: function(input) {
                var year = weekOfYear(this, this.lang()._week.dow, this.lang()._week.doy).year;
                return input == null ? year : this.add("y", input - year);
            },
            isoWeekYear: function(input) {
                var year = weekOfYear(this, 1, 4).year;
                return input == null ? year : this.add("y", input - year);
            },
            week: function(input) {
                var week = this.lang().week(this);
                return input == null ? week : this.add("d", (input - week) * 7);
            },
            isoWeek: function(input) {
                var week = weekOfYear(this, 1, 4).week;
                return input == null ? week : this.add("d", (input - week) * 7);
            },
            weekday: function(input) {
                var weekday = (this._d.getDay() + 7 - this.lang()._week.dow) % 7;
                return input == null ? weekday : this.add("d", input - weekday);
            },
            isoWeekday: function(input) {
                // behaves the same as moment#day except
                // as a getter, returns 7 instead of 0 (1-7 range instead of 0-6)
                // as a setter, sunday should belong to the previous week.
                return input == null ? this.day() || 7 : this.day(this.day() % 7 ? input : input - 7);
            },
            // If passed a language key, it will set the language for this
            // instance.  Otherwise, it will return the language configuration
            // variables for this instance.
            lang: function(key) {
                if (key === undefined) {
                    return this._lang;
                } else {
                    this._lang = getLangDefinition(key);
                    return this;
                }
            }
        };
        // helper for adding shortcuts
        function makeGetterAndSetter(name, key) {
            moment.fn[name] = moment.fn[name + "s"] = function(input) {
                var utc = this._isUTC ? "UTC" : "";
                if (input != null) {
                    this._d["set" + utc + key](input);
                    moment.updateOffset(this);
                    return this;
                } else {
                    return this._d["get" + utc + key]();
                }
            };
        }
        // loop through and add shortcuts (Month, Date, Hours, Minutes, Seconds, Milliseconds)
        for (i = 0; i < proxyGettersAndSetters.length; i++) {
            makeGetterAndSetter(proxyGettersAndSetters[i].toLowerCase().replace(/s$/, ""), proxyGettersAndSetters[i]);
        }
        // add shortcut for year (uses different syntax than the getter/setter 'year' == 'FullYear')
        makeGetterAndSetter("year", "FullYear");
        // add plural methods
        moment.fn.days = moment.fn.day;
        moment.fn.months = moment.fn.month;
        moment.fn.weeks = moment.fn.week;
        moment.fn.isoWeeks = moment.fn.isoWeek;
        // add aliased format methods
        moment.fn.toJSON = moment.fn.toISOString;
        /************************************
        Duration Prototype
    ************************************/
        moment.duration.fn = Duration.prototype = {
            _bubble: function() {
                var milliseconds = this._milliseconds, days = this._days, months = this._months, data = this._data, seconds, minutes, hours, years;
                // The following code bubbles up values, see the tests for
                // examples of what that means.
                data.milliseconds = milliseconds % 1e3;
                seconds = absRound(milliseconds / 1e3);
                data.seconds = seconds % 60;
                minutes = absRound(seconds / 60);
                data.minutes = minutes % 60;
                hours = absRound(minutes / 60);
                data.hours = hours % 24;
                days += absRound(hours / 24);
                data.days = days % 30;
                months += absRound(days / 30);
                data.months = months % 12;
                years = absRound(months / 12);
                data.years = years;
            },
            weeks: function() {
                return absRound(this.days() / 7);
            },
            valueOf: function() {
                return this._milliseconds + this._days * 864e5 + this._months % 12 * 2592e6 + ~~(this._months / 12) * 31536e6;
            },
            humanize: function(withSuffix) {
                var difference = +this, output = relativeTime(difference, !withSuffix, this.lang());
                if (withSuffix) {
                    output = this.lang().pastFuture(difference, output);
                }
                return this.lang().postformat(output);
            },
            add: function(input, val) {
                // supports only 2.0-style add(1, 's') or add(moment)
                var dur = moment.duration(input, val);
                this._milliseconds += dur._milliseconds;
                this._days += dur._days;
                this._months += dur._months;
                this._bubble();
                return this;
            },
            subtract: function(input, val) {
                var dur = moment.duration(input, val);
                this._milliseconds -= dur._milliseconds;
                this._days -= dur._days;
                this._months -= dur._months;
                this._bubble();
                return this;
            },
            get: function(units) {
                units = normalizeUnits(units);
                return this[units.toLowerCase() + "s"]();
            },
            as: function(units) {
                units = normalizeUnits(units);
                return this["as" + units.charAt(0).toUpperCase() + units.slice(1) + "s"]();
            },
            lang: moment.fn.lang
        };
        function makeDurationGetter(name) {
            moment.duration.fn[name] = function() {
                return this._data[name];
            };
        }
        function makeDurationAsGetter(name, factor) {
            moment.duration.fn["as" + name] = function() {
                return +this / factor;
            };
        }
        for (i in unitMillisecondFactors) {
            if (unitMillisecondFactors.hasOwnProperty(i)) {
                makeDurationAsGetter(i, unitMillisecondFactors[i]);
                makeDurationGetter(i.toLowerCase());
            }
        }
        makeDurationAsGetter("Weeks", 6048e5);
        moment.duration.fn.asMonths = function() {
            return (+this - this.years() * 31536e6) / 2592e6 + this.years() * 12;
        };
        /************************************
        Default Lang
    ************************************/
        // Set default language, other languages will inherit from English.
        moment.lang("en", {
            ordinal: function(number) {
                var b = number % 10, output = ~~(number % 100 / 10) === 1 ? "th" : b === 1 ? "st" : b === 2 ? "nd" : b === 3 ? "rd" : "th";
                return number + output;
            }
        });
        /************************************
        Exposing Moment
    ************************************/
        // CommonJS module is defined
        if (hasModule) {
            module.exports = moment;
        }
        /*global ender:false */
        if (typeof ender === "undefined") {
            // here, `this` means `window` in the browser, or `global` on the server
            // add `moment` as a global object via a string identifier,
            // for Closure Compiler "advanced" mode
            this["moment"] = moment;
        }
        /*global define:false */
        if (typeof define === "function" && define.amd) {
            define("moment", [], function() {
                return moment;
            });
        }
    }).call(this);
});

define("ikj/daterangepicker/1.2.0/daterangepicker-bs2-debug.css", [], function() {
    seajs.importStyle(".daterangepicker.dropdown-menu{max-width:none}.daterangepicker.opensleft .ranges,.daterangepicker.opensleft .calendar{float:left;margin:4px}.daterangepicker.opensright .ranges,.daterangepicker.opensright .calendar{float:right;margin:4px}.daterangepicker .ranges{width:160px;text-align:left}.daterangepicker .ranges .range_inputs>div{float:left}.daterangepicker .ranges .range_inputs>div:nth-child(2){padding-left:11px}.daterangepicker .calendar{display:none;max-width:250px}.daterangepicker .calendar th,.daterangepicker .calendar td{font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;white-space:nowrap;text-align:center}.daterangepicker .ranges label{color:#333;font-size:11px;margin-bottom:2px;text-transform:uppercase;text-shadow:1px 1px 0 #fff}.daterangepicker .ranges input{font-size:11px}.daterangepicker .ranges ul{list-style:none;margin:0;padding:0}.daterangepicker .ranges li{font-size:13px;background:#f5f5f5;border:1px solid #f5f5f5;color:#08c;padding:3px 12px;margin-bottom:8px;-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;cursor:pointer}.daterangepicker .ranges li.active,.daterangepicker .ranges li:hover{background:#08c;border:1px solid #08c;color:#fff}.daterangepicker .calendar-date{border:1px solid #ddd;padding:4px;border-radius:4px;background:#fff}.daterangepicker .calendar-time{text-align:center;margin:8px auto 0;line-height:30px}.daterangepicker{position:absolute;background:#fff;top:100px;left:20px;padding:4px;margin-top:1px;-webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px}.daterangepicker.opensleft:before{position:absolute;top:-7px;right:9px;display:inline-block;border-right:7px solid transparent;border-bottom:7px solid #ccc;border-left:7px solid transparent;border-bottom-color:rgba(0,0,0,.2);content:''}.daterangepicker.opensleft:after{position:absolute;top:-6px;right:10px;display:inline-block;border-right:6px solid transparent;border-bottom:6px solid #fff;border-left:6px solid transparent;content:''}.daterangepicker.opensright:before{position:absolute;top:-7px;left:9px;display:inline-block;border-right:7px solid transparent;border-bottom:7px solid #ccc;border-left:7px solid transparent;border-bottom-color:rgba(0,0,0,.2);content:''}.daterangepicker.opensright:after{position:absolute;top:-6px;left:10px;display:inline-block;border-right:6px solid transparent;border-bottom:6px solid #fff;border-left:6px solid transparent;content:''}.daterangepicker table{width:100%;margin:0}.daterangepicker td,.daterangepicker th{text-align:center;width:20px;height:20px;-webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px;cursor:pointer;white-space:nowrap}.daterangepicker td.off{color:#999}.daterangepicker td.disabled{color:#999}.daterangepicker td.available:hover,.daterangepicker th.available:hover{background:#eee}.daterangepicker td.in-range{background:#ebf4f8;-webkit-border-radius:0;-moz-border-radius:0;border-radius:0}.daterangepicker td.active,.daterangepicker td.active:hover{background-color:#006dcc;background-image:-moz-linear-gradient(top,#08c,#04c);background-image:-ms-linear-gradient(top,#08c,#04c);background-image:-webkit-gradient(linear,0 0,0 100%,from(#08c),to(#04c));background-image:-webkit-linear-gradient(top,#08c,#04c);background-image:-o-linear-gradient(top,#08c,#04c);background-image:linear-gradient(top,#08c,#04c);background-repeat:repeat-x;filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#0088cc', endColorstr='#0044cc', GradientType=0);border-color:#04c #04c #002a80;border-color:rgba(0,0,0,.1) rgba(0,0,0,.1) rgba(0,0,0,.25);filter:progid:DXImageTransform.Microsoft.gradient(enabled=false);color:#fff;text-shadow:0 -1px 0 rgba(0,0,0,.25)}.daterangepicker td.week,.daterangepicker th.week{font-size:80%;color:#ccc}.daterangepicker select.monthselect,.daterangepicker select.yearselect{font-size:12px;padding:1px;height:auto;margin:0;cursor:default}.daterangepicker select.monthselect{margin-right:2%;width:56%}.daterangepicker select.yearselect{width:40%}.daterangepicker select.hourselect,.daterangepicker select.minuteselect,.daterangepicker select.ampmselect{width:60px;margin-bottom:0}");
});
