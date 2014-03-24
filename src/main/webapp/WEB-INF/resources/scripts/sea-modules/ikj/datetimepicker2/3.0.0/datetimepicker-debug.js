define("ikj/datetimepicker2/3.0.0/datetimepicker-debug", [ "$-debug", "./moment-debug", "ikj/bootstrap/2.3.2/bootstrap-debug", "./datetimepicker.zh-CN-debug", "./datetimepicker-debug.css" ], function(require, exports, module) {
    "use strict";
    var jQuery = require("$-debug"), moment = require("./moment-debug");
    require("ikj/bootstrap/2.3.2/bootstrap-debug");
    require("./datetimepicker.zh-CN-debug");
    require("./datetimepicker-debug.css");
    (function(factory) {
        if (typeof define === "function" && define.amd) {
            // AMD is used - Register as an anonymous module.
            define([ "jquery", "moment" ], factory);
        } else {
            // AMD is not used - Attempt to fetch dependencies from scope.
            if (!jQuery) {
                throw "bootstrap-datetimepicker requires jQuery to be loaded first";
            } else if (!moment) {
                throw "bootstrap-datetimepicker requires moment.js to be loaded first";
            } else {
                factory(jQuery, moment);
            }
        }
    })(function($, moment) {
        if (typeof moment === "undefined") {
            alert("momentjs is requried");
            throw new Error("momentjs is required");
        }
        var dpgId = 0, pMoment = moment, // ReSharper disable once InconsistentNaming
        DateTimePicker = function(element, options) {
            var defaults = {
                pickDate: true,
                pickTime: true,
                useMinutes: true,
                useSeconds: false,
                useCurrent: true,
                minuteStepping: 1,
                minDate: new pMoment({
                    y: 1900
                }),
                maxDate: new pMoment().add(100, "y"),
                showToday: true,
                collapse: true,
                language: "en",
                defaultDate: "",
                disabledDates: false,
                enabledDates: false,
                icons: {},
                useStrict: false,
                direction: "auto",
                sideBySide: false,
                daysOfWeekDisabled: false
            }, icons = {
                time: "glyphicon glyphicon-time",
                date: "glyphicon glyphicon-calendar",
                //up: 'glyphicon glyphicon-chevron-up',
                up: "icon-chevron-up",
                down: "glyphicon glyphicon-chevron-down"
            }, picker = this, init = function() {
                var icon = false, i, dDate, longDateFormat;
                picker.options = $.extend({}, defaults, options);
                picker.options.icons = $.extend({}, icons, picker.options.icons);
                picker.element = $(element);
                dataToOptions();
                if (!(picker.options.pickTime || picker.options.pickDate)) throw new Error("Must choose at least one picker");
                picker.id = dpgId++;
                pMoment.lang(picker.options.language);
                picker.date = pMoment();
                picker.unset = false;
                picker.isInput = picker.element.is("input");
                picker.component = false;
                if (picker.element.hasClass("input-group")) {
                    if (picker.element.find(".datepickerbutton").size() == 0) {
                        //in case there is more then one 'input-group-addon' Issue #48
                        picker.component = picker.element.find("[class^='input-group-']");
                    } else {
                        picker.component = picker.element.find(".datepickerbutton");
                    }
                }
                picker.format = picker.options.format;
                longDateFormat = pMoment()._lang._longDateFormat;
                if (!picker.format) {
                    picker.format = picker.options.pickDate ? longDateFormat.L : "";
                    if (picker.options.pickDate && picker.options.pickTime) picker.format += " ";
                    picker.format += picker.options.pickTime ? longDateFormat.LT : "";
                    if (picker.options.useSeconds) {
                        if (~longDateFormat.LT.indexOf(" A")) {
                            picker.format = picker.format.split(" A")[0] + ":ss A";
                        } else {
                            picker.format += ":ss";
                        }
                    }
                }
                picker.use24hours = picker.format.toLowerCase().indexOf("a") < 1;
                if (picker.component) icon = picker.component.find("span");
                if (picker.options.pickTime) {
                    if (icon) icon.addClass(picker.options.icons.time);
                }
                if (picker.options.pickDate) {
                    if (icon) {
                        icon.removeClass(picker.options.icons.time);
                        icon.addClass(picker.options.icons.date);
                    }
                }
                picker.widget = $(getTemplate()).appendTo("body");
                if (picker.options.useSeconds && !picker.use24hours) {
                    picker.widget.width(300);
                }
                picker.minViewMode = picker.options.minViewMode || 0;
                if (typeof picker.minViewMode === "string") {
                    switch (picker.minViewMode) {
                      case "months":
                        picker.minViewMode = 1;
                        break;

                      case "years":
                        picker.minViewMode = 2;
                        break;

                      default:
                        picker.minViewMode = 0;
                        break;
                    }
                }
                picker.viewMode = picker.options.viewMode || 0;
                if (typeof picker.viewMode === "string") {
                    switch (picker.viewMode) {
                      case "months":
                        picker.viewMode = 1;
                        break;

                      case "years":
                        picker.viewMode = 2;
                        break;

                      default:
                        picker.viewMode = 0;
                        break;
                    }
                }
                picker.options.disabledDates = indexGivenDates(picker.options.disabledDates);
                picker.options.enabledDates = indexGivenDates(picker.options.enabledDates);
                picker.startViewMode = picker.viewMode;
                picker.setMinDate(picker.options.minDate);
                picker.setMaxDate(picker.options.maxDate);
                fillDow();
                fillMonths();
                fillHours();
                fillMinutes();
                fillSeconds();
                update();
                showMode();
                attachDatePickerEvents();
                if (picker.options.defaultDate !== "" && getPickerInput().val() == "") picker.setValue(picker.options.defaultDate);
                if (picker.options.minuteStepping !== 1) {
                    var rInterval = picker.options.minuteStepping;
                    picker.date.minutes(Math.round(picker.date.minutes() / rInterval) * rInterval % 60).seconds(0);
                }
            }, getPickerInput = function() {
                if (picker.isInput) {
                    return picker.element;
                } else {
                    return dateStr = picker.element.find("input");
                }
            }, dataToOptions = function() {
                var eData;
                if (picker.element.is("input")) {
                    eData = picker.element.data();
                } else {
                    eData = picker.element.data();
                }
                if (eData.dateFormat !== undefined) picker.options.format = eData.dateFormat;
                if (eData.datePickdate !== undefined) picker.options.pickDate = eData.datePickdate;
                if (eData.datePicktime !== undefined) picker.options.pickTime = eData.datePicktime;
                if (eData.dateUseminutes !== undefined) picker.options.useMinutes = eData.dateUseminutes;
                if (eData.dateUseseconds !== undefined) picker.options.useSeconds = eData.dateUseseconds;
                if (eData.dateUsecurrent !== undefined) picker.options.useCurrent = eData.dateUsecurrent;
                if (eData.dateMinutestepping !== undefined) picker.options.minuteStepping = eData.dateMinutestepping;
                if (eData.dateMindate !== undefined) picker.options.minDate = eData.dateMindate;
                if (eData.dateMaxdate !== undefined) picker.options.maxDate = eData.dateMaxdate;
                if (eData.dateShowtoday !== undefined) picker.options.showToday = eData.dateShowtoday;
                if (eData.dateCollapse !== undefined) picker.options.collapse = eData.dateCollapse;
                if (eData.dateLanguage !== undefined) picker.options.language = eData.dateLanguage;
                if (eData.dateDefaultdate !== undefined) picker.options.defaultDate = eData.dateDefaultdate;
                if (eData.dateDisableddates !== undefined) picker.options.disabledDates = eData.dateDisableddates;
                if (eData.dateEnableddates !== undefined) picker.options.enabledDates = eData.dateEnableddates;
                if (eData.dateIcons !== undefined) picker.options.icons = eData.dateIcons;
                if (eData.dateUsestrict !== undefined) picker.options.useStrict = eData.dateUsestrict;
                if (eData.dateDirection !== undefined) picker.options.direction = eData.dateDirection;
                if (eData.dateSidebyside !== undefined) picker.options.sideBySide = eData.dateSidebyside;
            }, place = function() {
                var position = "absolute", offset = picker.component ? picker.component.offset() : picker.element.offset(), $window = $(window);
                picker.width = picker.component ? picker.component.outerWidth() : picker.element.outerWidth();
                offset.top = offset.top + picker.element.outerHeight();
                var placePosition;
                if (picker.options.direction === "up") {
                    placePosition = "top";
                } else if (picker.options.direction === "bottom") {
                    placePosition = "bottom";
                } else if (picker.options.direction === "auto") {
                    if (offset.top + picker.widget.height() > $window.height() + $window.scrollTop() && picker.widget.height() + picker.element.outerHeight() < offset.top) {
                        placePosition = "top";
                    } else {
                        placePosition = "bottom";
                    }
                }
                if (placePosition === "top") {
                    offset.top -= picker.widget.height() + picker.element.outerHeight() + 15;
                    picker.widget.addClass("top").removeClass("bottom");
                } else {
                    offset.top += 1;
                    picker.widget.addClass("bottom").removeClass("top");
                }
                if (picker.options.width !== undefined) {
                    picker.widget.width(picker.options.width);
                }
                if (picker.options.orientation === "left") {
                    picker.widget.addClass("left-oriented");
                    offset.left = offset.left - picker.widget.width() + 20;
                }
                if (isInFixed()) {
                    position = "fixed";
                    offset.top -= $window.scrollTop();
                    offset.left -= $window.scrollLeft();
                }
                if ($window.width() < offset.left + picker.widget.outerWidth()) {
                    offset.right = $window.width() - offset.left - picker.width;
                    offset.left = "auto";
                    picker.widget.addClass("pull-right");
                } else {
                    offset.right = "auto";
                    picker.widget.removeClass("pull-right");
                }
                picker.widget.css({
                    position: position,
                    top: offset.top,
                    left: offset.left,
                    right: offset.right
                });
            }, notifyChange = function(oldDate, eventType) {
                if (pMoment(picker.date).isSame(pMoment(oldDate))) return;
                picker.element.trigger({
                    type: "dp.change",
                    date: pMoment(picker.date),
                    oldDate: pMoment(oldDate)
                });
                if (eventType !== "change") picker.element.change();
            }, notifyError = function(date) {
                picker.element.trigger({
                    type: "dp.error",
                    date: pMoment(date)
                });
            }, update = function(newDate) {
                pMoment.lang(picker.options.language);
                var dateStr = newDate;
                if (!dateStr) {
                    dateStr = getPickerInput().val();
                    if (dateStr) picker.date = pMoment(dateStr, picker.format, picker.options.useStrict);
                    if (!picker.date) picker.date = pMoment();
                }
                picker.viewDate = pMoment(picker.date).startOf("month");
                fillDate();
                fillTime();
            }, fillDow = function() {
                pMoment.lang(picker.options.language);
                var html = $("<tr>"), weekdaysMin = pMoment.weekdaysMin(), i;
                if (pMoment()._lang._week.dow == 0) {
                    // starts on Sunday
                    for (i = 0; i < 7; i++) {
                        html.append('<th class="dow">' + weekdaysMin[i] + "</th>");
                    }
                } else {
                    for (i = 1; i < 8; i++) {
                        if (i == 7) {
                            html.append('<th class="dow">' + weekdaysMin[0] + "</th>");
                        } else {
                            html.append('<th class="dow">' + weekdaysMin[i] + "</th>");
                        }
                    }
                }
                picker.widget.find(".datepicker-days thead").append(html);
            }, fillMonths = function() {
                pMoment.lang(picker.options.language);
                var html = "", i = 0, monthsShort = pMoment.monthsShort();
                while (i < 12) {
                    html += '<span class="month">' + monthsShort[i++] + "</span>";
                }
                picker.widget.find(".datepicker-months td").append(html);
            }, fillDate = function() {
                pMoment.lang(picker.options.language);
                var year = picker.viewDate.year(), month = picker.viewDate.month(), startYear = picker.options.minDate.year(), startMonth = picker.options.minDate.month(), endYear = picker.options.maxDate.year(), endMonth = picker.options.maxDate.month(), prevMonth, nextMonth, html = [], row, clsName, i, days, yearCont, currentYear, months = pMoment.months();
                picker.widget.find(".datepicker-days").find(".disabled").removeClass("disabled");
                picker.widget.find(".datepicker-months").find(".disabled").removeClass("disabled");
                picker.widget.find(".datepicker-years").find(".disabled").removeClass("disabled");
                picker.widget.find(".datepicker-days th:eq(1)").text(months[month] + " " + year);
                prevMonth = pMoment(picker.viewDate).subtract("months", 1);
                days = prevMonth.daysInMonth();
                prevMonth.date(days).startOf("week");
                if (year == startYear && month <= startMonth || year < startYear) {
                    picker.widget.find(".datepicker-days th:eq(0)").addClass("disabled");
                }
                if (year == endYear && month >= endMonth || year > endYear) {
                    picker.widget.find(".datepicker-days th:eq(2)").addClass("disabled");
                }
                nextMonth = pMoment(prevMonth).add(42, "d");
                while (prevMonth.isBefore(nextMonth)) {
                    if (prevMonth.weekday() === pMoment().startOf("week").weekday()) {
                        row = $("<tr>");
                        html.push(row);
                    }
                    clsName = "";
                    if (prevMonth.year() < year || prevMonth.year() == year && prevMonth.month() < month) {
                        clsName += " old";
                    } else if (prevMonth.year() > year || prevMonth.year() == year && prevMonth.month() > month) {
                        clsName += " new";
                    }
                    if (prevMonth.isSame(pMoment({
                        y: picker.date.year(),
                        M: picker.date.month(),
                        d: picker.date.date()
                    }))) {
                        clsName += " active";
                    }
                    if (isInDisableDates(prevMonth) || !isInEnableDates(prevMonth)) {
                        clsName += " disabled";
                    }
                    if (picker.options.showToday === true) {
                        if (prevMonth.isSame(pMoment(), "day")) {
                            clsName += " today";
                        }
                    }
                    if (picker.options.daysOfWeekDisabled) {
                        for (i in picker.options.daysOfWeekDisabled) {
                            if (prevMonth.day() == picker.options.daysOfWeekDisabled[i]) {
                                clsName += " disabled";
                                break;
                            }
                        }
                    }
                    row.append('<td class="day' + clsName + '">' + prevMonth.date() + "</td>");
                    prevMonth.add(1, "d");
                }
                picker.widget.find(".datepicker-days tbody").empty().append(html);
                currentYear = picker.date.year(), months = picker.widget.find(".datepicker-months").find("th:eq(1)").text(year).end().find("span").removeClass("active");
                if (currentYear === year) {
                    months.eq(picker.date.month()).addClass("active");
                }
                if (currentYear - 1 < startYear) {
                    picker.widget.find(".datepicker-months th:eq(0)").addClass("disabled");
                }
                if (currentYear + 1 > endYear) {
                    picker.widget.find(".datepicker-months th:eq(2)").addClass("disabled");
                }
                for (i = 0; i < 12; i++) {
                    if (year == startYear && startMonth > i || year < startYear) {
                        $(months[i]).addClass("disabled");
                    } else if (year == endYear && endMonth < i || year > endYear) {
                        $(months[i]).addClass("disabled");
                    }
                }
                html = "";
                year = parseInt(year / 10, 10) * 10;
                yearCont = picker.widget.find(".datepicker-years").find("th:eq(1)").text(year + "-" + (year + 9)).end().find("td");
                picker.widget.find(".datepicker-years").find("th").removeClass("disabled");
                if (startYear > year) {
                    picker.widget.find(".datepicker-years").find("th:eq(0)").addClass("disabled");
                }
                if (endYear < year + 9) {
                    picker.widget.find(".datepicker-years").find("th:eq(2)").addClass("disabled");
                }
                year -= 1;
                for (i = -1; i < 11; i++) {
                    html += '<span class="year' + (i === -1 || i === 10 ? " old" : "") + (currentYear === year ? " active" : "") + (year < startYear || year > endYear ? " disabled" : "") + '">' + year + "</span>";
                    year += 1;
                }
                yearCont.html(html);
            }, fillHours = function() {
                pMoment.lang(picker.options.language);
                var table = picker.widget.find(".timepicker .timepicker-hours table"), html = "", current, i, j;
                table.parent().hide();
                if (picker.use24hours) {
                    current = 0;
                    for (i = 0; i < 6; i += 1) {
                        html += "<tr>";
                        for (j = 0; j < 4; j += 1) {
                            html += '<td class="hour">' + padLeft(current.toString()) + "</td>";
                            current++;
                        }
                        html += "</tr>";
                    }
                } else {
                    current = 1;
                    for (i = 0; i < 3; i += 1) {
                        html += "<tr>";
                        for (j = 0; j < 4; j += 1) {
                            html += '<td class="hour">' + padLeft(current.toString()) + "</td>";
                            current++;
                        }
                        html += "</tr>";
                    }
                }
                table.html(html);
            }, fillMinutes = function() {
                var table = picker.widget.find(".timepicker .timepicker-minutes table"), html = "", current = 0, i, j, step = picker.options.minuteStepping;
                table.parent().hide();
                if (step == 1) step = 5;
                for (i = 0; i < Math.ceil(60 / step / 4); i++) {
                    html += "<tr>";
                    for (j = 0; j < 4; j += 1) {
                        if (current < 60) {
                            html += '<td class="minute">' + padLeft(current.toString()) + "</td>";
                            current += step;
                        } else {
                            html += "<td></td>";
                        }
                    }
                    html += "</tr>";
                }
                table.html(html);
            }, fillSeconds = function() {
                var table = picker.widget.find(".timepicker .timepicker-seconds table"), html = "", current = 0, i, j;
                table.parent().hide();
                for (i = 0; i < 3; i++) {
                    html += "<tr>";
                    for (j = 0; j < 4; j += 1) {
                        html += '<td class="second">' + padLeft(current.toString()) + "</td>";
                        current += 5;
                    }
                    html += "</tr>";
                }
                table.html(html);
            }, fillTime = function() {
                if (!picker.date) return;
                var timeComponents = picker.widget.find(".timepicker span[data-time-component]"), hour = picker.date.hours(), period = "AM";
                if (!picker.use24hours) {
                    if (hour >= 12) period = "PM";
                    if (hour === 0) hour = 12; else if (hour != 12) hour = hour % 12;
                    picker.widget.find(".timepicker [data-action=togglePeriod]").text(period);
                }
                timeComponents.filter("[data-time-component=hours]").text(padLeft(hour));
                timeComponents.filter("[data-time-component=minutes]").text(padLeft(picker.date.minutes()));
                timeComponents.filter("[data-time-component=seconds]").text(padLeft(picker.date.second()));
            }, click = function(e) {
                e.stopPropagation();
                e.preventDefault();
                picker.unset = false;
                var target = $(e.target).closest("span, td, th"), month, year, step, day, oldDate = pMoment(picker.date);
                if (target.length === 1) {
                    if (!target.is(".disabled")) {
                        switch (target[0].nodeName.toLowerCase()) {
                          case "th":
                            switch (target[0].className) {
                              case "switch":
                                showMode(1);
                                break;

                              case "prev":
                              case "next":
                                step = dpGlobal.modes[picker.viewMode].navStep;
                                if (target[0].className === "prev") step = step * -1;
                                picker.viewDate.add(step, dpGlobal.modes[picker.viewMode].navFnc);
                                fillDate();
                                break;
                            }
                            break;

                          case "span":
                            if (target.is(".month")) {
                                month = target.parent().find("span").index(target);
                                picker.viewDate.month(month);
                            } else {
                                year = parseInt(target.text(), 10) || 0;
                                picker.viewDate.year(year);
                            }
                            if (picker.viewMode === picker.minViewMode) {
                                picker.date = pMoment({
                                    y: picker.viewDate.year(),
                                    M: picker.viewDate.month(),
                                    d: picker.viewDate.date(),
                                    h: picker.date.hours(),
                                    m: picker.date.minutes(),
                                    s: picker.date.seconds()
                                });
                                notifyChange(oldDate, e.type);
                                set();
                            }
                            showMode(-1);
                            fillDate();
                            break;

                          case "td":
                            if (target.is(".day")) {
                                day = parseInt(target.text(), 10) || 1;
                                month = picker.viewDate.month();
                                year = picker.viewDate.year();
                                if (target.is(".old")) {
                                    if (month === 0) {
                                        month = 11;
                                        year -= 1;
                                    } else {
                                        month -= 1;
                                    }
                                } else if (target.is(".new")) {
                                    if (month == 11) {
                                        month = 0;
                                        year += 1;
                                    } else {
                                        month += 1;
                                    }
                                }
                                picker.date = pMoment({
                                    y: year,
                                    M: month,
                                    d: day,
                                    h: picker.date.hours(),
                                    m: picker.date.minutes(),
                                    s: picker.date.seconds()
                                });
                                picker.viewDate = pMoment({
                                    y: year,
                                    M: month,
                                    d: Math.min(28, day)
                                });
                                fillDate();
                                set();
                                notifyChange(oldDate, e.type);
                            }
                            break;
                        }
                    }
                }
            }, actions = {
                incrementHours: function() {
                    checkDate("add", "hours", 1);
                },
                incrementMinutes: function() {
                    checkDate("add", "minutes", picker.options.minuteStepping);
                },
                incrementSeconds: function() {
                    checkDate("add", "seconds", 1);
                },
                decrementHours: function() {
                    checkDate("subtract", "hours", 1);
                },
                decrementMinutes: function() {
                    checkDate("subtract", "minutes", picker.options.minuteStepping);
                },
                decrementSeconds: function() {
                    checkDate("subtract", "seconds", 1);
                },
                togglePeriod: function() {
                    var hour = picker.date.hours();
                    if (hour >= 12) hour -= 12; else hour += 12;
                    picker.date.hours(hour);
                },
                showPicker: function() {
                    picker.widget.find(".timepicker > div:not(.timepicker-picker)").hide();
                    picker.widget.find(".timepicker .timepicker-picker").show();
                },
                showHours: function() {
                    picker.widget.find(".timepicker .timepicker-picker").hide();
                    picker.widget.find(".timepicker .timepicker-hours").show();
                },
                showMinutes: function() {
                    picker.widget.find(".timepicker .timepicker-picker").hide();
                    picker.widget.find(".timepicker .timepicker-minutes").show();
                },
                showSeconds: function() {
                    picker.widget.find(".timepicker .timepicker-picker").hide();
                    picker.widget.find(".timepicker .timepicker-seconds").show();
                },
                selectHour: function(e) {
                    var period = picker.widget.find(".timepicker [data-action=togglePeriod]").text(), hour = parseInt($(e.target).text(), 10);
                    if (period == "PM") hour += 12;
                    picker.date.hours(hour);
                    actions.showPicker.call(picker);
                },
                selectMinute: function(e) {
                    picker.date.minutes(parseInt($(e.target).text(), 10));
                    actions.showPicker.call(picker);
                },
                selectSecond: function(e) {
                    picker.date.seconds(parseInt($(e.target).text(), 10));
                    actions.showPicker.call(picker);
                }
            }, doAction = function(e) {
                var oldDate = pMoment(picker.date), action = $(e.currentTarget).data("action"), rv = actions[action].apply(picker, arguments);
                stopEvent(e);
                if (!picker.date) picker.date = pMoment({
                    y: 1970
                });
                set();
                fillTime();
                notifyChange(oldDate, e.type);
                return rv;
            }, stopEvent = function(e) {
                e.stopPropagation();
                e.preventDefault();
            }, change = function(e) {
                pMoment.lang(picker.options.language);
                var input = $(e.target), oldDate = pMoment(picker.date), newDate = pMoment(input.val(), picker.format, picker.options.useStrict);
                if (newDate.isValid() && !isInDisableDates(newDate) && isInEnableDates(newDate)) {
                    update();
                    picker.setValue(newDate);
                    notifyChange(oldDate, e.type);
                    set();
                } else {
                    picker.viewDate = oldDate;
                    notifyChange(oldDate, e.type);
                    notifyError(newDate);
                    picker.unset = true;
                }
            }, showMode = function(dir) {
                if (dir) {
                    picker.viewMode = Math.max(picker.minViewMode, Math.min(2, picker.viewMode + dir));
                }
                var f = dpGlobal.modes[picker.viewMode].clsName;
                picker.widget.find(".datepicker > div").hide().filter(".datepicker-" + dpGlobal.modes[picker.viewMode].clsName).show();
            }, attachDatePickerEvents = function() {
                var $this, $parent, expanded, closed, collapseData;
                picker.widget.on("click", ".datepicker *", $.proxy(click, this));
                // this handles date picker clicks
                picker.widget.on("click", "[data-action]", $.proxy(doAction, this));
                // this handles time picker clicks
                picker.widget.on("mousedown", $.proxy(stopEvent, this));
                if (picker.options.pickDate && picker.options.pickTime) {
                    picker.widget.on("click.togglePicker", ".accordion-toggle", function(e) {
                        e.stopPropagation();
                        $this = $(this);
                        $parent = $this.closest("ul");
                        expanded = $parent.find(".in");
                        closed = $parent.find(".collapse:not(.in)");
                        if (expanded && expanded.length) {
                            collapseData = expanded.data("collapse");
                            //console.log(transitioning);
                            //if (collapseData && collapseData.date - transitioning) return;
                            expanded.collapse("hide");
                            closed.collapse("show");
                            $this.find("span").toggleClass(picker.options.icons.time + " " + picker.options.icons.date);
                            picker.element.find(".input-group-addon span").toggleClass(picker.options.icons.time + " " + picker.options.icons.date);
                        }
                    });
                }
                if (picker.isInput) {
                    picker.element.on({
                        focus: $.proxy(picker.show, this),
                        change: $.proxy(change, this),
                        blur: $.proxy(picker.hide, this)
                    });
                } else {
                    picker.element.on({
                        change: $.proxy(change, this)
                    }, "input");
                    if (picker.component) {
                        picker.component.on("click", $.proxy(picker.show, this));
                    } else {
                        picker.element.on("click", $.proxy(picker.show, this));
                    }
                }
            }, attachDatePickerGlobalEvents = function() {
                $(window).on("resize.datetimepicker" + picker.id, $.proxy(place, this));
                if (!picker.isInput) {
                    $(document).on("mousedown.datetimepicker" + picker.id, $.proxy(picker.hide, this));
                }
            }, detachDatePickerEvents = function() {
                picker.widget.off("click", ".datepicker *", picker.click);
                picker.widget.off("click", "[data-action]");
                picker.widget.off("mousedown", picker.stopEvent);
                if (picker.options.pickDate && picker.options.pickTime) {
                    picker.widget.off("click.togglePicker");
                }
                if (picker.isInput) {
                    picker.element.off({
                        focus: picker.show,
                        change: picker.change
                    });
                } else {
                    picker.element.off({
                        change: picker.change
                    }, "input");
                    if (picker.component) {
                        picker.component.off("click", picker.show);
                    } else {
                        picker.element.off("click", picker.show);
                    }
                }
            }, detachDatePickerGlobalEvents = function() {
                $(window).off("resize.datetimepicker" + picker.id);
                if (!picker.isInput) {
                    $(document).off("mousedown.datetimepicker" + picker.id);
                }
            }, isInFixed = function() {
                if (picker.element) {
                    var parents = picker.element.parents(), inFixed = false, i;
                    for (i = 0; i < parents.length; i++) {
                        if ($(parents[i]).css("position") == "fixed") {
                            inFixed = true;
                            break;
                        }
                    }
                    return inFixed;
                } else {
                    return false;
                }
            }, set = function() {
                pMoment.lang(picker.options.language);
                var formatted = "", input;
                if (!picker.unset) formatted = pMoment(picker.date).format(picker.format);
                getPickerInput().val(formatted);
                picker.element.data("date", formatted);
                if (!picker.options.pickTime) picker.hide();
            }, checkDate = function(direction, unit, amount) {
                pMoment.lang(picker.options.language);
                var newDate;
                if (direction == "add") {
                    newDate = pMoment(picker.date);
                    if (newDate.hours() == 23) newDate.add(amount, unit);
                    newDate.add(amount, unit);
                } else {
                    newDate = pMoment(picker.date).subtract(amount, unit);
                }
                if (isInDisableDates(pMoment(newDate.subtract(amount, unit))) || isInDisableDates(newDate)) {
                    notifyError(newDate.format(picker.format));
                    return;
                }
                if (direction == "add") {
                    picker.date.add(amount, unit);
                } else {
                    picker.date.subtract(amount, unit);
                }
                picker.unset = false;
            }, isInDisableDates = function(date) {
                pMoment.lang(picker.options.language);
                if (date.isAfter(picker.options.maxDate) || date.isBefore(picker.options.minDate)) return true;
                if (picker.options.disabledDates === false) {
                    return false;
                }
                return picker.options.disabledDates[pMoment(date).format("YYYY-MM-DD")] === true;
            }, isInEnableDates = function(date) {
                pMoment.lang(picker.options.language);
                if (picker.options.enabledDates === false) {
                    return true;
                }
                return picker.options.enabledDates[pMoment(date).format("YYYY-MM-DD")] === true;
            }, indexGivenDates = function(givenDatesArray) {
                // Store given enabledDates and disabledDates as keys.
                // This way we can check their existence in O(1) time instead of looping through whole array.
                // (for example: picker.options.enabledDates['2014-02-27'] === true)
                var givenDatesIndexed = {};
                var givenDatesCount = 0;
                for (var i = 0; i < givenDatesArray.length; i++) {
                    dDate = pMoment(givenDatesArray[i]);
                    if (dDate.isValid()) {
                        givenDatesIndexed[dDate.format("YYYY-MM-DD")] = true;
                        givenDatesCount++;
                    }
                }
                if (givenDatesCount > 0) {
                    return givenDatesIndexed;
                }
                return false;
            }, padLeft = function(string) {
                string = string.toString();
                if (string.length >= 2) return string; else return "0" + string;
            }, getTemplate = function() {
                if (picker.options.pickDate && picker.options.pickTime) {
                    var ret = "";
                    ret = '<div class="bootstrap-datetimepicker-widget' + (picker.options.sideBySide ? " timepicker-sbs" : "") + ' dropdown-menu" style="z-index:9999 !important;">';
                    if (picker.options.sideBySide) {
                        ret += '<div class="row">' + '<div class="col-sm-6 datepicker">' + dpGlobal.template + "</div>" + '<div class="col-sm-6 timepicker">' + tpGlobal.getTemplate() + "</div>" + "</div>";
                    } else {
                        ret += '<ul class="list-unstyled">' + "<li" + (picker.options.collapse ? ' class="collapse in"' : "") + ">" + '<div class="datepicker">' + dpGlobal.template + "</div>" + "</li>" + '<li class="picker-switch accordion-toggle"><a class="btn" style="width:100%"><span class="' + picker.options.icons.time + '"></span></a></li>' + "<li" + (picker.options.collapse ? ' class="collapse"' : "") + ">" + '<div class="timepicker">' + tpGlobal.getTemplate() + "</div>" + "</li>" + "</ul>";
                    }
                    ret += "</div>";
                    return ret;
                } else if (picker.options.pickTime) {
                    return '<div class="bootstrap-datetimepicker-widget dropdown-menu">' + '<div class="timepicker">' + tpGlobal.getTemplate() + "</div>" + "</div>";
                } else {
                    return '<div class="bootstrap-datetimepicker-widget dropdown-menu">' + '<div class="datepicker">' + dpGlobal.template + "</div>" + "</div>";
                }
            }, dpGlobal = {
                modes: [ {
                    clsName: "days",
                    navFnc: "month",
                    navStep: 1
                }, {
                    clsName: "months",
                    navFnc: "year",
                    navStep: 1
                }, {
                    clsName: "years",
                    navFnc: "year",
                    navStep: 10
                } ],
                headTemplate: "<thead>" + "<tr>" + '<th class="prev">&lsaquo;</th><th colspan="5" class="switch"></th><th class="next">&rsaquo;</th>' + "</tr>" + "</thead>",
                contTemplate: '<tbody><tr><td colspan="7"></td></tr></tbody>'
            }, tpGlobal = {
                hourTemplate: '<span data-action="showHours"   data-time-component="hours"   class="timepicker-hour"></span>',
                minuteTemplate: '<span data-action="showMinutes" data-time-component="minutes" class="timepicker-minute"></span>',
                secondTemplate: '<span data-action="showSeconds"  data-time-component="seconds" class="timepicker-second"></span>'
            };
            dpGlobal.template = '<div class="datepicker-days">' + '<table class="table-condensed">' + dpGlobal.headTemplate + "<tbody></tbody></table>" + "</div>" + '<div class="datepicker-months">' + '<table class="table-condensed">' + dpGlobal.headTemplate + dpGlobal.contTemplate + "</table>" + "</div>" + '<div class="datepicker-years">' + '<table class="table-condensed">' + dpGlobal.headTemplate + dpGlobal.contTemplate + "</table>" + "</div>";
            tpGlobal.getTemplate = function() {
                return '<div class="timepicker-picker">' + '<table class="table-condensed">' + "<tr>" + '<td><a href="#" class="btn" data-action="incrementHours"><span class="' + picker.options.icons.up + '"></span></a></td>' + '<td class="separator"></td>' + "<td>" + (picker.options.useMinutes ? '<a href="#" class="btn" data-action="incrementMinutes"><span class="' + picker.options.icons.up + '"></span></a>' : "") + "</td>" + (picker.options.useSeconds ? '<td class="separator"></td><td><a href="#" class="btn" data-action="incrementSeconds"><span class="' + picker.options.icons.up + '"></span></a></td>' : "") + (picker.use24hours ? "" : '<td class="separator"></td>') + "</tr>" + "<tr>" + "<td>" + tpGlobal.hourTemplate + "</td> " + '<td class="separator">:</td>' + "<td>" + (picker.options.useMinutes ? tpGlobal.minuteTemplate : '<span class="timepicker-minute">00</span>') + "</td> " + (picker.options.useSeconds ? '<td class="separator">:</td><td>' + tpGlobal.secondTemplate + "</td>" : "") + (picker.use24hours ? "" : '<td class="separator"></td>' + '<td><button type="button" class="btn btn-primary" data-action="togglePeriod"></button></td>') + "</tr>" + "<tr>" + '<td><a href="#" class="btn" data-action="decrementHours"><span class="' + picker.options.icons.down + '"></span></a></td>' + '<td class="separator"></td>' + "<td>" + (picker.options.useMinutes ? '<a href="#" class="btn" data-action="decrementMinutes"><span class="' + picker.options.icons.down + '"></span></a>' : "") + "</td>" + (picker.options.useSeconds ? '<td class="separator"></td><td><a href="#" class="btn" data-action="decrementSeconds"><span class="' + picker.options.icons.down + '"></span></a></td>' : "") + (picker.use24hours ? "" : '<td class="separator"></td>') + "</tr>" + "</table>" + "</div>" + '<div class="timepicker-hours" data-action="selectHour">' + '<table class="table-condensed"></table>' + "</div>" + '<div class="timepicker-minutes" data-action="selectMinute">' + '<table class="table-condensed"></table>' + "</div>" + (picker.options.useSeconds ? '<div class="timepicker-seconds" data-action="selectSecond"><table class="table-condensed"></table></div>' : "");
            };
            picker.destroy = function() {
                detachDatePickerEvents();
                detachDatePickerGlobalEvents();
                picker.widget.remove();
                picker.element.removeData("DateTimePicker");
                if (picker.component) picker.component.removeData("DateTimePicker");
            };
            picker.show = function(e) {
                if (picker.options.useCurrent) {
                    if (getPickerInput().val() == "") {
                        if (picker.options.minuteStepping !== 1) {
                            var mDate = pMoment(), rInterval = picker.options.minuteStepping;
                            mDate.minutes(Math.round(mDate.minutes() / rInterval) * rInterval % 60).seconds(0);
                            picker.setValue(mDate.format(picker.format));
                        } else {
                            picker.setValue(pMoment().format(picker.format));
                        }
                    }
                }
                picker.widget.show();
                picker.height = picker.component ? picker.component.outerHeight() : picker.element.outerHeight();
                place();
                picker.element.trigger({
                    type: "dp.show",
                    date: pMoment(picker.date)
                });
                attachDatePickerGlobalEvents();
                if (e) {
                    stopEvent(e);
                }
            }, picker.disable = function() {
                var input = picker.element.find("input");
                if (input.prop("disabled")) return;
                input.prop("disabled", true);
                detachDatePickerEvents();
            }, picker.enable = function() {
                var input = picker.element.find("input");
                if (!input.prop("disabled")) return;
                input.prop("disabled", false);
                attachDatePickerEvents();
            }, picker.hide = function(event) {
                if (event && $(event.target).is(picker.element.attr("id"))) return;
                // Ignore event if in the middle of a picker transition
                var collapse = picker.widget.find(".collapse"), i, collapseData;
                //for (i = 0; i < collapse.length; i++) {
                //collapseData = collapse.eq(i).data('collapse');
                //if (collapseData && collapseData.date - transitioning)
                //return;
                //}
                picker.widget.hide();
                picker.viewMode = picker.startViewMode;
                showMode();
                picker.element.trigger({
                    type: "dp.hide",
                    date: pMoment(picker.date)
                });
                detachDatePickerGlobalEvents();
            }, picker.setValue = function(newDate) {
                pMoment.lang(picker.options.language);
                if (!newDate) {
                    picker.unset = true;
                    set();
                } else {
                    picker.unset = false;
                }
                if (!pMoment.isMoment(newDate)) newDate = pMoment(newDate);
                if (newDate.isValid()) {
                    picker.date = newDate;
                    set();
                    picker.viewDate = pMoment({
                        y: picker.date.year(),
                        M: picker.date.month()
                    });
                    fillDate();
                    fillTime();
                } else {
                    notifyError(newDate);
                }
            }, picker.getDate = function() {
                if (picker.unset) return null;
                return picker.date;
            }, picker.setDate = function(date) {
                var oldDate = pMoment(picker.date);
                if (!date) {
                    picker.setValue(null);
                } else {
                    picker.setValue(date);
                }
                notifyChange(oldDate, "function");
            }, picker.setDisabledDates = function(dates) {
                picker.options.disabledDates = indexGivenDates(dates);
                if (picker.viewDate) update();
            }, picker.setEnabledDates = function(dates) {
                picker.options.enabledDates = indexGivenDates(dates);
                if (picker.viewDate) update();
            }, picker.setMaxDate = function(date) {
                if (date == undefined) return;
                picker.options.maxDate = pMoment(date);
                if (picker.viewDate) update();
            }, picker.setMinDate = function(date) {
                if (date == undefined) return;
                picker.options.minDate = pMoment(date);
                if (picker.viewDate) update();
            };
            init();
        };
        $.fn.datetimepicker = function(options) {
            var defaults = {
                language: "zh-cn",
                useSeconds: true,
                format: "YYYY-MM-DD HH:mm:ss",
                icons: {
                    time: "icon-time",
                    date: "icon-calendar",
                    up: "icon-chevron-up",
                    down: "icon-chevron-down"
                }
            };
            options = $.extend({}, defaults, options);
            return this.each(function() {
                var $this = $(this), data = $this.data("DateTimePicker");
                if (!data) $this.data("DateTimePicker", new DateTimePicker(this, options));
            });
        };
    });
});

define("ikj/datetimepicker2/3.0.0/moment-debug", [], function(require, exports, module) {
    "use strict";
    //! moment.js
    //! version : 2.5.1
    //! authors : Tim Wood, Iskren Chernev, Moment.js contributors
    //! license : MIT
    //! momentjs.com
    (function(undefined) {
        /************************************
        Constants
    ************************************/
        var moment, VERSION = "2.5.1", global = this, round = Math.round, i, YEAR = 0, MONTH = 1, DATE = 2, HOUR = 3, MINUTE = 4, SECOND = 5, MILLISECOND = 6, // internal storage for language config files
        languages = {}, // moment internal properties
        momentProperties = {
            _isAMomentObject: null,
            _i: null,
            _f: null,
            _l: null,
            _strict: null,
            _isUTC: null,
            _offset: null,
            // optional. Combine with _isUTC
            _pf: null,
            _lang: null
        }, // check for nodeJS
        hasModule = typeof module !== "undefined" && module.exports && typeof require !== "undefined", // ASP.NET json date format regex
        aspNetJsonRegex = /^\/?Date\((\-?\d+)/i, aspNetTimeSpanJsonRegex = /(\-)?(?:(\d*)\.)?(\d+)\:(\d+)(?:\:(\d+)\.?(\d{3})?)?/, // from http://docs.closure-library.googlecode.com/git/closure_goog_date_date.js.source.html
        // somewhat more in line with 4.4.3.2 2004 spec, but allows decimal anywhere
        isoDurationRegex = /^(-)?P(?:(?:([0-9,.]*)Y)?(?:([0-9,.]*)M)?(?:([0-9,.]*)D)?(?:T(?:([0-9,.]*)H)?(?:([0-9,.]*)M)?(?:([0-9,.]*)S)?)?|([0-9,.]*)W)$/, // format tokens
        formattingTokens = /(\[[^\[]*\])|(\\)?(Mo|MM?M?M?|Do|DDDo|DD?D?D?|ddd?d?|do?|w[o|w]?|W[o|W]?|YYYYYY|YYYYY|YYYY|YY|gg(ggg?)?|GG(GGG?)?|e|E|a|A|hh?|HH?|mm?|ss?|S{1,4}|X|zz?|ZZ?|.)/g, localFormattingTokens = /(\[[^\[]*\])|(\\)?(LT|LL?L?L?|l{1,4})/g, // parsing token regexes
        parseTokenOneOrTwoDigits = /\d\d?/, // 0 - 99
        parseTokenOneToThreeDigits = /\d{1,3}/, // 0 - 999
        parseTokenOneToFourDigits = /\d{1,4}/, // 0 - 9999
        parseTokenOneToSixDigits = /[+\-]?\d{1,6}/, // -999,999 - 999,999
        parseTokenDigits = /\d+/, // nonzero number of digits
        parseTokenWord = /[0-9]*['a-z\u00A0-\u05FF\u0700-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+|[\u0600-\u06FF\/]+(\s*?[\u0600-\u06FF]+){1,2}/i, // any word (or two) characters or numbers including two/three word month in arabic.
        parseTokenTimezone = /Z|[\+\-]\d\d:?\d\d/gi, // +00:00 -00:00 +0000 -0000 or Z
        parseTokenT = /T/i, // T (ISO separator)
        parseTokenTimestampMs = /[\+\-]?\d+(\.\d{1,3})?/, // 123456789 123456789.123
        parseTokenOrdinal = /\d{1,2}/, //strict parsing regexes
        parseTokenOneDigit = /\d/, // 0 - 9
        parseTokenTwoDigits = /\d\d/, // 00 - 99
        parseTokenThreeDigits = /\d{3}/, // 000 - 999
        parseTokenFourDigits = /\d{4}/, // 0000 - 9999
        parseTokenSixDigits = /[+-]?\d{6}/, // -999,999 - 999,999
        parseTokenSignedNumber = /[+-]?\d+/, // -inf - inf
        // iso 8601 regex
        // 0000-00-00 0000-W00 or 0000-W00-0 + T + 00 or 00:00 or 00:00:00 or 00:00:00.000 + +00:00 or +0000 or +00)
        isoRegex = /^\s*(?:[+-]\d{6}|\d{4})-(?:(\d\d-\d\d)|(W\d\d$)|(W\d\d-\d)|(\d\d\d))((T| )(\d\d(:\d\d(:\d\d(\.\d+)?)?)?)?([\+\-]\d\d(?::?\d\d)?|\s*Z)?)?$/, isoFormat = "YYYY-MM-DDTHH:mm:ssZ", isoDates = [ [ "YYYYYY-MM-DD", /[+-]\d{6}-\d{2}-\d{2}/ ], [ "YYYY-MM-DD", /\d{4}-\d{2}-\d{2}/ ], [ "GGGG-[W]WW-E", /\d{4}-W\d{2}-\d/ ], [ "GGGG-[W]WW", /\d{4}-W\d{2}/ ], [ "YYYY-DDD", /\d{4}-\d{3}/ ] ], // iso time formats and regexes
        isoTimes = [ [ "HH:mm:ss.SSSS", /(T| )\d\d:\d\d:\d\d\.\d{1,3}/ ], [ "HH:mm:ss", /(T| )\d\d:\d\d:\d\d/ ], [ "HH:mm", /(T| )\d\d:\d\d/ ], [ "HH", /(T| )\d\d/ ] ], // timezone chunker "+10:00" > ["10", "00"] or "-1530" > ["-15", "30"]
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
            D: "date",
            w: "week",
            W: "isoWeek",
            M: "month",
            y: "year",
            DDD: "dayOfYear",
            e: "weekday",
            E: "isoWeekday",
            gg: "weekYear",
            GG: "isoWeekYear"
        }, camelFunctions = {
            dayofyear: "dayOfYear",
            isoweekday: "isoWeekday",
            isoweek: "isoWeek",
            weekyear: "weekYear",
            isoweekyear: "isoWeekYear"
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
            YYYYYY: function() {
                var y = this.year(), sign = y >= 0 ? "+" : "-";
                return sign + leftZeroFill(Math.abs(y), 6);
            },
            gg: function() {
                return leftZeroFill(this.weekYear() % 100, 2);
            },
            gggg: function() {
                return leftZeroFill(this.weekYear(), 4);
            },
            ggggg: function() {
                return leftZeroFill(this.weekYear(), 5);
            },
            GG: function() {
                return leftZeroFill(this.isoWeekYear() % 100, 2);
            },
            GGGG: function() {
                return leftZeroFill(this.isoWeekYear(), 4);
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
                return toInt(this.milliseconds() / 100);
            },
            SS: function() {
                return leftZeroFill(toInt(this.milliseconds() / 10), 2);
            },
            SSS: function() {
                return leftZeroFill(this.milliseconds(), 3);
            },
            SSSS: function() {
                return leftZeroFill(this.milliseconds(), 3);
            },
            Z: function() {
                var a = -this.zone(), b = "+";
                if (a < 0) {
                    a = -a;
                    b = "-";
                }
                return b + leftZeroFill(toInt(a / 60), 2) + ":" + leftZeroFill(toInt(a) % 60, 2);
            },
            ZZ: function() {
                var a = -this.zone(), b = "+";
                if (a < 0) {
                    a = -a;
                    b = "-";
                }
                return b + leftZeroFill(toInt(a / 60), 2) + leftZeroFill(toInt(a) % 60, 2);
            },
            z: function() {
                return this.zoneAbbr();
            },
            zz: function() {
                return this.zoneName();
            },
            X: function() {
                return this.unix();
            },
            Q: function() {
                return this.quarter();
            }
        }, lists = [ "months", "monthsShort", "weekdays", "weekdaysShort", "weekdaysMin" ];
        function defaultParsingFlags() {
            // We need to deep clone this object, and es5 standard is not very
            // helpful.
            return {
                empty: false,
                unusedTokens: [],
                unusedInput: [],
                overflow: -2,
                charsLeftOver: 0,
                nullInput: false,
                invalidMonth: null,
                invalidFormat: false,
                userInvalidated: false,
                iso: false
            };
        }
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
            checkOverflow(config);
            extend(this, config);
        }
        // Duration Constructor
        function Duration(duration) {
            var normalizedInput = normalizeObjectUnits(duration), years = normalizedInput.year || 0, months = normalizedInput.month || 0, weeks = normalizedInput.week || 0, days = normalizedInput.day || 0, hours = normalizedInput.hour || 0, minutes = normalizedInput.minute || 0, seconds = normalizedInput.second || 0, milliseconds = normalizedInput.millisecond || 0;
            // representation for dateAddRemove
            this._milliseconds = +milliseconds + seconds * 1e3 + // 1000
            minutes * 6e4 + // 1000 * 60
            hours * 36e5;
            // 1000 * 60 * 60
            // Because of dateAddRemove treats 24 hours as different from a
            // day when working around DST, we need to store them separately
            this._days = +days + weeks * 7;
            // It is impossible translate months into days without knowing
            // which months you are are talking about, so we have to store
            // it separately.
            this._months = +months + years * 12;
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
            if (b.hasOwnProperty("toString")) {
                a.toString = b.toString;
            }
            if (b.hasOwnProperty("valueOf")) {
                a.valueOf = b.valueOf;
            }
            return a;
        }
        function cloneMoment(m) {
            var result = {}, i;
            for (i in m) {
                if (m.hasOwnProperty(i) && momentProperties.hasOwnProperty(i)) {
                    result[i] = m[i];
                }
            }
            return result;
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
        function leftZeroFill(number, targetLength, forceSign) {
            var output = "" + Math.abs(number), sign = number >= 0;
            while (output.length < targetLength) {
                output = "0" + output;
            }
            return (sign ? forceSign ? "+" : "" : "-") + output;
        }
        // helper function for _.addTime and _.subtractTime
        function addOrSubtractDurationFromMoment(mom, duration, isAdding, ignoreUpdateOffset) {
            var milliseconds = duration._milliseconds, days = duration._days, months = duration._months, minutes, hours;
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
                moment.updateOffset(mom, days || months);
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
        function isDate(input) {
            return Object.prototype.toString.call(input) === "[object Date]" || input instanceof Date;
        }
        // compare two arrays, return the number of differences
        function compareArrays(array1, array2, dontConvert) {
            var len = Math.min(array1.length, array2.length), lengthDiff = Math.abs(array1.length - array2.length), diffs = 0, i;
            for (i = 0; i < len; i++) {
                if (dontConvert && array1[i] !== array2[i] || !dontConvert && toInt(array1[i]) !== toInt(array2[i])) {
                    diffs++;
                }
            }
            return diffs + lengthDiff;
        }
        function normalizeUnits(units) {
            if (units) {
                var lowered = units.toLowerCase().replace(/(.)s$/, "$1");
                units = unitAliases[units] || camelFunctions[lowered] || lowered;
            }
            return units;
        }
        function normalizeObjectUnits(inputObject) {
            var normalizedInput = {}, normalizedProp, prop;
            for (prop in inputObject) {
                if (inputObject.hasOwnProperty(prop)) {
                    normalizedProp = normalizeUnits(prop);
                    if (normalizedProp) {
                        normalizedInput[normalizedProp] = inputObject[prop];
                    }
                }
            }
            return normalizedInput;
        }
        function makeList(field) {
            var count, setter;
            if (field.indexOf("week") === 0) {
                count = 7;
                setter = "day";
            } else if (field.indexOf("month") === 0) {
                count = 12;
                setter = "month";
            } else {
                return;
            }
            moment[field] = function(format, index) {
                var i, getter, method = moment.fn._lang[field], results = [];
                if (typeof format === "number") {
                    index = format;
                    format = undefined;
                }
                getter = function(i) {
                    var m = moment().utc().set(setter, i);
                    return method.call(moment.fn._lang, m, format || "");
                };
                if (index != null) {
                    return getter(index);
                } else {
                    for (i = 0; i < count; i++) {
                        results.push(getter(i));
                    }
                    return results;
                }
            };
        }
        function toInt(argumentForCoercion) {
            var coercedNumber = +argumentForCoercion, value = 0;
            if (coercedNumber !== 0 && isFinite(coercedNumber)) {
                if (coercedNumber >= 0) {
                    value = Math.floor(coercedNumber);
                } else {
                    value = Math.ceil(coercedNumber);
                }
            }
            return value;
        }
        function daysInMonth(year, month) {
            return new Date(Date.UTC(year, month + 1, 0)).getUTCDate();
        }
        function weeksInYear(year, dow, doy) {
            return weekOfYear(moment([ year, 11, 31 + dow - doy ]), dow, doy).week;
        }
        function daysInYear(year) {
            return isLeapYear(year) ? 366 : 365;
        }
        function isLeapYear(year) {
            return year % 4 === 0 && year % 100 !== 0 || year % 400 === 0;
        }
        function checkOverflow(m) {
            var overflow;
            if (m._a && m._pf.overflow === -2) {
                overflow = m._a[MONTH] < 0 || m._a[MONTH] > 11 ? MONTH : m._a[DATE] < 1 || m._a[DATE] > daysInMonth(m._a[YEAR], m._a[MONTH]) ? DATE : m._a[HOUR] < 0 || m._a[HOUR] > 23 ? HOUR : m._a[MINUTE] < 0 || m._a[MINUTE] > 59 ? MINUTE : m._a[SECOND] < 0 || m._a[SECOND] > 59 ? SECOND : m._a[MILLISECOND] < 0 || m._a[MILLISECOND] > 999 ? MILLISECOND : -1;
                if (m._pf._overflowDayOfYear && (overflow < YEAR || overflow > DATE)) {
                    overflow = DATE;
                }
                m._pf.overflow = overflow;
            }
        }
        function isValid(m) {
            if (m._isValid == null) {
                m._isValid = !isNaN(m._d.getTime()) && m._pf.overflow < 0 && !m._pf.empty && !m._pf.invalidMonth && !m._pf.nullInput && !m._pf.invalidFormat && !m._pf.userInvalidated;
                if (m._strict) {
                    m._isValid = m._isValid && m._pf.charsLeftOver === 0 && m._pf.unusedTokens.length === 0;
                }
            }
            return m._isValid;
        }
        function normalizeLanguage(key) {
            return key ? key.toLowerCase().replace("_", "-") : key;
        }
        // Return a moment from input, that is local/utc/zone equivalent to model.
        function makeAs(input, model) {
            return model._isUTC ? moment(input).zone(model._offset || 0) : moment(input).local();
        }
        /************************************
        Languages
    ************************************/
        extend(Language.prototype, {
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
                        mom = moment.utc([ 2e3, i ]);
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
                // IE8 Quirks Mode & IE7 Standards Mode do not allow accessing strings like arrays
                // Using charAt should be more compatible.
                return (input + "").toLowerCase().charAt(0) === "p";
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
            },
            _invalidDate: "Invalid date",
            invalidDate: function() {
                return this._invalidDate;
            }
        });
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
        // Remove a language from the `languages` cache. Mostly useful in tests.
        function unloadLang(key) {
            delete languages[key];
        }
        // Determines which language definition to use and returns it.
        //
        // With no parameters, it will return the global language.  If you
        // pass in a language key, such as 'en', it will return the
        // definition for 'en', so long as 'en' has already been loaded using
        // moment.lang.
        function getLangDefinition(key) {
            var i = 0, j, lang, next, split, get = function(k) {
                if (!languages[k] && hasModule) {
                    try {
                        require("./lang/" + k);
                    } catch (e) {}
                }
                return languages[k];
            };
            if (!key) {
                return moment.fn._lang;
            }
            if (!isArray(key)) {
                //short-circuit everything else
                lang = get(key);
                if (lang) {
                    return lang;
                }
                key = [ key ];
            }
            //pick the language from the array
            //try ['en-au', 'en-gb'] as 'en-au', 'en-gb', 'en', as in move through the list trying each
            //substring from most specific to least, but move to the next array item if it's a more specific variant than the current root
            while (i < key.length) {
                split = normalizeLanguage(key[i]).split("-");
                j = split.length;
                next = normalizeLanguage(key[i + 1]);
                next = next ? next.split("-") : null;
                while (j > 0) {
                    lang = get(split.slice(0, j).join("-"));
                    if (lang) {
                        return lang;
                    }
                    if (next && next.length >= j && compareArrays(split, next, true) >= j - 1) {
                        //the next array item is better than a shallower substring of this one
                        break;
                    }
                    j--;
                }
                i++;
            }
            return moment.fn._lang;
        }
        /************************************
        Formatting
    ************************************/
        function removeFormattingTokens(input) {
            if (input.match(/\[[\s\S]/)) {
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
            if (!m.isValid()) {
                return m.lang().invalidDate();
            }
            format = expandFormat(format, m.lang());
            if (!formatFunctions[format]) {
                formatFunctions[format] = makeFormatFunction(format);
            }
            return formatFunctions[format](m);
        }
        function expandFormat(format, lang) {
            var i = 5;
            function replaceLongDateFormatTokens(input) {
                return lang.longDateFormat(input) || input;
            }
            localFormattingTokens.lastIndex = 0;
            while (i >= 0 && localFormattingTokens.test(format)) {
                format = format.replace(localFormattingTokens, replaceLongDateFormatTokens);
                localFormattingTokens.lastIndex = 0;
                i -= 1;
            }
            return format;
        }
        /************************************
        Parsing
    ************************************/
        // get the regex to find the next token
        function getParseRegexForToken(token, config) {
            var a, strict = config._strict;
            switch (token) {
              case "DDDD":
                return parseTokenThreeDigits;

              case "YYYY":
              case "GGGG":
              case "gggg":
                return strict ? parseTokenFourDigits : parseTokenOneToFourDigits;

              case "Y":
              case "G":
              case "g":
                return parseTokenSignedNumber;

              case "YYYYYY":
              case "YYYYY":
              case "GGGGG":
              case "ggggg":
                return strict ? parseTokenSixDigits : parseTokenOneToSixDigits;

              case "S":
                if (strict) {
                    return parseTokenOneDigit;
                }

              /* falls through */
                case "SS":
                if (strict) {
                    return parseTokenTwoDigits;
                }

              /* falls through */
                case "SSS":
                if (strict) {
                    return parseTokenThreeDigits;
                }

              /* falls through */
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

              case "SSSS":
                return parseTokenDigits;

              case "MM":
              case "DD":
              case "YY":
              case "GG":
              case "gg":
              case "HH":
              case "hh":
              case "mm":
              case "ss":
              case "ww":
              case "WW":
                return strict ? parseTokenTwoDigits : parseTokenOneOrTwoDigits;

              case "M":
              case "D":
              case "d":
              case "H":
              case "h":
              case "m":
              case "s":
              case "w":
              case "W":
              case "e":
              case "E":
                return parseTokenOneOrTwoDigits;

              case "Do":
                return parseTokenOrdinal;

              default:
                a = new RegExp(regexpEscape(unescapeFormat(token.replace("\\", "")), "i"));
                return a;
            }
        }
        function timezoneMinutesFromString(string) {
            string = string || "";
            var possibleTzMatches = string.match(parseTokenTimezone) || [], tzChunk = possibleTzMatches[possibleTzMatches.length - 1] || [], parts = (tzChunk + "").match(parseTimezoneChunker) || [ "-", 0, 0 ], minutes = +(parts[1] * 60) + toInt(parts[2]);
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
                if (input != null) {
                    datePartArray[MONTH] = toInt(input) - 1;
                }
                break;

              case "MMM":
              // fall through to MMMM
                case "MMMM":
                a = getLangDefinition(config._l).monthsParse(input);
                // if we didn't find a month name, mark the date as invalid.
                if (a != null) {
                    datePartArray[MONTH] = a;
                } else {
                    config._pf.invalidMonth = input;
                }
                break;

              // DAY OF MONTH
                case "D":
              // fall through to DD
                case "DD":
                if (input != null) {
                    datePartArray[DATE] = toInt(input);
                }
                break;

              case "Do":
                if (input != null) {
                    datePartArray[DATE] = toInt(parseInt(input, 10));
                }
                break;

              // DAY OF YEAR
                case "DDD":
              // fall through to DDDD
                case "DDDD":
                if (input != null) {
                    config._dayOfYear = toInt(input);
                }
                break;

              // YEAR
                case "YY":
                datePartArray[YEAR] = toInt(input) + (toInt(input) > 68 ? 1900 : 2e3);
                break;

              case "YYYY":
              case "YYYYY":
              case "YYYYYY":
                datePartArray[YEAR] = toInt(input);
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
                datePartArray[HOUR] = toInt(input);
                break;

              // MINUTE
                case "m":
              // fall through to mm
                case "mm":
                datePartArray[MINUTE] = toInt(input);
                break;

              // SECOND
                case "s":
              // fall through to ss
                case "ss":
                datePartArray[SECOND] = toInt(input);
                break;

              // MILLISECOND
                case "S":
              case "SS":
              case "SSS":
              case "SSSS":
                datePartArray[MILLISECOND] = toInt(("0." + input) * 1e3);
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

              case "w":
              case "ww":
              case "W":
              case "WW":
              case "d":
              case "dd":
              case "ddd":
              case "dddd":
              case "e":
              case "E":
                token = token.substr(0, 1);

              /* falls through */
                case "gg":
              case "gggg":
              case "GG":
              case "GGGG":
              case "GGGGG":
                token = token.substr(0, 2);
                if (input) {
                    config._w = config._w || {};
                    config._w[token] = input;
                }
                break;
            }
        }
        // convert an array to a date.
        // the array should mirror the parameters below
        // note: all values past the year are optional and will default to the lowest possible value.
        // [year, month, day , hour, minute, second, millisecond]
        function dateFromConfig(config) {
            var i, date, input = [], currentDate, yearToUse, fixYear, w, temp, lang, weekday, week;
            if (config._d) {
                return;
            }
            currentDate = currentDateArray(config);
            //compute day of the year from weeks and weekdays
            if (config._w && config._a[DATE] == null && config._a[MONTH] == null) {
                fixYear = function(val) {
                    var int_val = parseInt(val, 10);
                    return val ? val.length < 3 ? int_val > 68 ? 1900 + int_val : 2e3 + int_val : int_val : config._a[YEAR] == null ? moment().weekYear() : config._a[YEAR];
                };
                w = config._w;
                if (w.GG != null || w.W != null || w.E != null) {
                    temp = dayOfYearFromWeeks(fixYear(w.GG), w.W || 1, w.E, 4, 1);
                } else {
                    lang = getLangDefinition(config._l);
                    weekday = w.d != null ? parseWeekday(w.d, lang) : w.e != null ? parseInt(w.e, 10) + lang._week.dow : 0;
                    week = parseInt(w.w, 10) || 1;
                    //if we're parsing 'd', then the low day numbers may be next week
                    if (w.d != null && weekday < lang._week.dow) {
                        week++;
                    }
                    temp = dayOfYearFromWeeks(fixYear(w.gg), week, weekday, lang._week.doy, lang._week.dow);
                }
                config._a[YEAR] = temp.year;
                config._dayOfYear = temp.dayOfYear;
            }
            //if the day of the year is set, figure out what it is
            if (config._dayOfYear) {
                yearToUse = config._a[YEAR] == null ? currentDate[YEAR] : config._a[YEAR];
                if (config._dayOfYear > daysInYear(yearToUse)) {
                    config._pf._overflowDayOfYear = true;
                }
                date = makeUTCDate(yearToUse, 0, config._dayOfYear);
                config._a[MONTH] = date.getUTCMonth();
                config._a[DATE] = date.getUTCDate();
            }
            // Default to current date.
            // * if no year, month, day of month are given, default to today
            // * if day of month is given, default month and year
            // * if month is given, default only year
            // * if year is given, don't default anything
            for (i = 0; i < 3 && config._a[i] == null; ++i) {
                config._a[i] = input[i] = currentDate[i];
            }
            // Zero out whatever was not defaulted, including time
            for (;i < 7; i++) {
                config._a[i] = input[i] = config._a[i] == null ? i === 2 ? 1 : 0 : config._a[i];
            }
            // add the offsets to the time to be parsed so that we can have a clean array for checking isValid
            input[HOUR] += toInt((config._tzm || 0) / 60);
            input[MINUTE] += toInt((config._tzm || 0) % 60);
            config._d = (config._useUTC ? makeUTCDate : makeDate).apply(null, input);
        }
        function dateFromObject(config) {
            var normalizedInput;
            if (config._d) {
                return;
            }
            normalizedInput = normalizeObjectUnits(config._i);
            config._a = [ normalizedInput.year, normalizedInput.month, normalizedInput.day, normalizedInput.hour, normalizedInput.minute, normalizedInput.second, normalizedInput.millisecond ];
            dateFromConfig(config);
        }
        function currentDateArray(config) {
            var now = new Date();
            if (config._useUTC) {
                return [ now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate() ];
            } else {
                return [ now.getFullYear(), now.getMonth(), now.getDate() ];
            }
        }
        // date from string and format string
        function makeDateFromStringAndFormat(config) {
            config._a = [];
            config._pf.empty = true;
            // This array is used to make a Date, either with `new Date` or `Date.UTC`
            var lang = getLangDefinition(config._l), string = "" + config._i, i, parsedInput, tokens, token, skipped, stringLength = string.length, totalParsedInputLength = 0;
            tokens = expandFormat(config._f, lang).match(formattingTokens) || [];
            for (i = 0; i < tokens.length; i++) {
                token = tokens[i];
                parsedInput = (string.match(getParseRegexForToken(token, config)) || [])[0];
                if (parsedInput) {
                    skipped = string.substr(0, string.indexOf(parsedInput));
                    if (skipped.length > 0) {
                        config._pf.unusedInput.push(skipped);
                    }
                    string = string.slice(string.indexOf(parsedInput) + parsedInput.length);
                    totalParsedInputLength += parsedInput.length;
                }
                // don't parse if it's not a known token
                if (formatTokenFunctions[token]) {
                    if (parsedInput) {
                        config._pf.empty = false;
                    } else {
                        config._pf.unusedTokens.push(token);
                    }
                    addTimeToArrayFromToken(token, parsedInput, config);
                } else if (config._strict && !parsedInput) {
                    config._pf.unusedTokens.push(token);
                }
            }
            // add remaining unparsed input length to the string
            config._pf.charsLeftOver = stringLength - totalParsedInputLength;
            if (string.length > 0) {
                config._pf.unusedInput.push(string);
            }
            // handle am pm
            if (config._isPm && config._a[HOUR] < 12) {
                config._a[HOUR] += 12;
            }
            // if is 12 am, change hours to 0
            if (config._isPm === false && config._a[HOUR] === 12) {
                config._a[HOUR] = 0;
            }
            dateFromConfig(config);
            checkOverflow(config);
        }
        function unescapeFormat(s) {
            return s.replace(/\\(\[)|\\(\])|\[([^\]\[]*)\]|\\(.)/g, function(matched, p1, p2, p3, p4) {
                return p1 || p2 || p3 || p4;
            });
        }
        // Code from http://stackoverflow.com/questions/3561493/is-there-a-regexp-escape-function-in-javascript
        function regexpEscape(s) {
            return s.replace(/[-\/\\^$*+?.()|[\]{}]/g, "\\$&");
        }
        // date from string and array of format strings
        function makeDateFromStringAndArray(config) {
            var tempConfig, bestMoment, scoreToBeat, i, currentScore;
            if (config._f.length === 0) {
                config._pf.invalidFormat = true;
                config._d = new Date(NaN);
                return;
            }
            for (i = 0; i < config._f.length; i++) {
                currentScore = 0;
                tempConfig = extend({}, config);
                tempConfig._pf = defaultParsingFlags();
                tempConfig._f = config._f[i];
                makeDateFromStringAndFormat(tempConfig);
                if (!isValid(tempConfig)) {
                    continue;
                }
                // if there is any input that was not parsed add a penalty for that format
                currentScore += tempConfig._pf.charsLeftOver;
                //or tokens
                currentScore += tempConfig._pf.unusedTokens.length * 10;
                tempConfig._pf.score = currentScore;
                if (scoreToBeat == null || currentScore < scoreToBeat) {
                    scoreToBeat = currentScore;
                    bestMoment = tempConfig;
                }
            }
            extend(config, bestMoment || tempConfig);
        }
        // date from iso format
        function makeDateFromString(config) {
            var i, l, string = config._i, match = isoRegex.exec(string);
            if (match) {
                config._pf.iso = true;
                for (i = 0, l = isoDates.length; i < l; i++) {
                    if (isoDates[i][1].exec(string)) {
                        // match[5] should be "T" or undefined
                        config._f = isoDates[i][0] + (match[6] || " ");
                        break;
                    }
                }
                for (i = 0, l = isoTimes.length; i < l; i++) {
                    if (isoTimes[i][1].exec(string)) {
                        config._f += isoTimes[i][0];
                        break;
                    }
                }
                if (string.match(parseTokenTimezone)) {
                    config._f += "Z";
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
                dateFromConfig(config);
            } else if (isDate(input)) {
                config._d = new Date(+input);
            } else if (typeof input === "object") {
                dateFromObject(config);
            } else {
                config._d = new Date(input);
            }
        }
        function makeDate(y, m, d, h, M, s, ms) {
            //can't just apply() to create a date:
            //http://stackoverflow.com/questions/181348/instantiating-a-javascript-object-by-calling-prototype-constructor-apply
            var date = new Date(y, m, d, h, M, s, ms);
            //the date constructor doesn't accept years < 1970
            if (y < 1970) {
                date.setFullYear(y);
            }
            return date;
        }
        function makeUTCDate(y) {
            var date = new Date(Date.UTC.apply(null, arguments));
            if (y < 1970) {
                date.setUTCFullYear(y);
            }
            return date;
        }
        function parseWeekday(input, language) {
            if (typeof input === "string") {
                if (!isNaN(input)) {
                    input = parseInt(input, 10);
                } else {
                    input = language.weekdaysParse(input);
                    if (typeof input !== "number") {
                        return null;
                    }
                }
            }
            return input;
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
        //http://en.wikipedia.org/wiki/ISO_week_date#Calculating_a_date_given_the_year.2C_week_number_and_weekday
        function dayOfYearFromWeeks(year, week, weekday, firstDayOfWeekOfYear, firstDayOfWeek) {
            var d = makeUTCDate(year, 0, 1).getUTCDay(), daysToAdd, dayOfYear;
            weekday = weekday != null ? weekday : firstDayOfWeek;
            daysToAdd = firstDayOfWeek - d + (d > firstDayOfWeekOfYear ? 7 : 0) - (d < firstDayOfWeek ? 7 : 0);
            dayOfYear = 7 * (week - 1) + (weekday - firstDayOfWeek) + daysToAdd + 1;
            return {
                year: dayOfYear > 0 ? year : year - 1,
                dayOfYear: dayOfYear > 0 ? dayOfYear : daysInYear(year - 1) + dayOfYear
            };
        }
        /************************************
        Top Level Functions
    ************************************/
        function makeMoment(config) {
            var input = config._i, format = config._f;
            if (input === null) {
                return moment.invalid({
                    nullInput: true
                });
            }
            if (typeof input === "string") {
                config._i = input = getLangDefinition().preparse(input);
            }
            if (moment.isMoment(input)) {
                config = cloneMoment(input);
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
        moment = function(input, format, lang, strict) {
            var c;
            if (typeof lang === "boolean") {
                strict = lang;
                lang = undefined;
            }
            // object construction must be done this way.
            // https://github.com/moment/moment/issues/1423
            c = {};
            c._isAMomentObject = true;
            c._i = input;
            c._f = format;
            c._l = lang;
            c._strict = strict;
            c._isUTC = false;
            c._pf = defaultParsingFlags();
            return makeMoment(c);
        };
        // creating with utc
        moment.utc = function(input, format, lang, strict) {
            var c;
            if (typeof lang === "boolean") {
                strict = lang;
                lang = undefined;
            }
            // object construction must be done this way.
            // https://github.com/moment/moment/issues/1423
            c = {};
            c._isAMomentObject = true;
            c._useUTC = true;
            c._isUTC = true;
            c._l = lang;
            c._i = input;
            c._f = format;
            c._strict = strict;
            c._pf = defaultParsingFlags();
            return makeMoment(c).utc();
        };
        // creating with unix timestamp (in seconds)
        moment.unix = function(input) {
            return moment(input * 1e3);
        };
        // duration
        moment.duration = function(input, key) {
            var duration = input, // matching against regexp is expensive, do it on demand
            match = null, sign, ret, parseIso;
            if (moment.isDuration(input)) {
                duration = {
                    ms: input._milliseconds,
                    d: input._days,
                    M: input._months
                };
            } else if (typeof input === "number") {
                duration = {};
                if (key) {
                    duration[key] = input;
                } else {
                    duration.milliseconds = input;
                }
            } else if (!!(match = aspNetTimeSpanJsonRegex.exec(input))) {
                sign = match[1] === "-" ? -1 : 1;
                duration = {
                    y: 0,
                    d: toInt(match[DATE]) * sign,
                    h: toInt(match[HOUR]) * sign,
                    m: toInt(match[MINUTE]) * sign,
                    s: toInt(match[SECOND]) * sign,
                    ms: toInt(match[MILLISECOND]) * sign
                };
            } else if (!!(match = isoDurationRegex.exec(input))) {
                sign = match[1] === "-" ? -1 : 1;
                parseIso = function(inp) {
                    // We'd normally use ~~inp for this, but unfortunately it also
                    // converts floats to ints.
                    // inp may be undefined, so careful calling replace on it.
                    var res = inp && parseFloat(inp.replace(",", "."));
                    // apply sign while we're at it
                    return (isNaN(res) ? 0 : res) * sign;
                };
                duration = {
                    y: parseIso(match[2]),
                    M: parseIso(match[3]),
                    d: parseIso(match[4]),
                    h: parseIso(match[5]),
                    m: parseIso(match[6]),
                    s: parseIso(match[7]),
                    w: parseIso(match[8])
                };
            }
            ret = new Duration(duration);
            if (moment.isDuration(input) && input.hasOwnProperty("_lang")) {
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
            var r;
            if (!key) {
                return moment.fn._lang._abbr;
            }
            if (values) {
                loadLang(normalizeLanguage(key), values);
            } else if (values === null) {
                unloadLang(key);
                key = "en";
            } else if (!languages[key]) {
                getLangDefinition(key);
            }
            r = moment.duration.fn._lang = moment.fn._lang = getLangDefinition(key);
            return r._abbr;
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
            return obj instanceof Moment || obj != null && obj.hasOwnProperty("_isAMomentObject");
        };
        // for typechecking Duration objects
        moment.isDuration = function(obj) {
            return obj instanceof Duration;
        };
        for (i = lists.length - 1; i >= 0; --i) {
            makeList(lists[i]);
        }
        moment.normalizeUnits = function(units) {
            return normalizeUnits(units);
        };
        moment.invalid = function(flags) {
            var m = moment.utc(NaN);
            if (flags != null) {
                extend(m._pf, flags);
            } else {
                m._pf.userInvalidated = true;
            }
            return m;
        };
        moment.parseZone = function() {
            return moment.apply(null, arguments).parseZone();
        };
        /************************************
        Moment Prototype
    ************************************/
        extend(moment.fn = Moment.prototype, {
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
                return this.clone().lang("en").format("ddd MMM DD YYYY HH:mm:ss [GMT]ZZ");
            },
            toDate: function() {
                return this._offset ? new Date(+this) : this._d;
            },
            toISOString: function() {
                var m = moment(this).utc();
                if (0 < m.year() && m.year() <= 9999) {
                    return formatMoment(m, "YYYY-MM-DD[T]HH:mm:ss.SSS[Z]");
                } else {
                    return formatMoment(m, "YYYYYY-MM-DD[T]HH:mm:ss.SSS[Z]");
                }
            },
            toArray: function() {
                var m = this;
                return [ m.year(), m.month(), m.date(), m.hours(), m.minutes(), m.seconds(), m.milliseconds() ];
            },
            isValid: function() {
                return isValid(this);
            },
            isDSTShifted: function() {
                if (this._a) {
                    return this.isValid() && compareArrays(this._a, (this._isUTC ? moment.utc(this._a) : moment(this._a)).toArray()) > 0;
                }
                return false;
            },
            parsingFlags: function() {
                return extend({}, this._pf);
            },
            invalidAt: function() {
                return this._pf.overflow;
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
                var that = makeAs(input, this), zoneDiff = (this.zone() - that.zone()) * 6e4, diff, output;
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
                // We want to compare the start of today, vs this.
                // Getting start-of-today depends on whether we're zone'd or not.
                var sod = makeAs(moment(), this).startOf("day"), diff = this.diff(sod, "days", true), format = diff < -6 ? "sameElse" : diff < -1 ? "lastWeek" : diff < 0 ? "lastDay" : diff < 1 ? "sameDay" : diff < 2 ? "nextDay" : diff < 7 ? "nextWeek" : "sameElse";
                return this.format(this.lang().calendar(format, this));
            },
            isLeapYear: function() {
                return isLeapYear(this.year());
            },
            isDST: function() {
                return this.zone() < this.clone().month(0).zone() || this.zone() < this.clone().month(5).zone();
            },
            day: function(input) {
                var day = this._isUTC ? this._d.getUTCDay() : this._d.getDay();
                if (input != null) {
                    input = parseWeekday(input, this.lang());
                    return this.add({
                        d: input - day
                    });
                } else {
                    return day;
                }
            },
            month: function(input) {
                var utc = this._isUTC ? "UTC" : "", dayOfMonth;
                if (input != null) {
                    if (typeof input === "string") {
                        input = this.lang().monthsParse(input);
                        if (typeof input !== "number") {
                            return this;
                        }
                    }
                    dayOfMonth = Math.min(this.date(), daysInMonth(this.year(), input));
                    this._d["set" + utc + "Month"](input, dayOfMonth);
                    moment.updateOffset(this, true);
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
                  case "isoWeek":
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
                } else if (units === "isoWeek") {
                    this.isoWeekday(1);
                }
                return this;
            },
            endOf: function(units) {
                units = normalizeUnits(units);
                return this.startOf(units).add(units === "isoWeek" ? "week" : units, 1).subtract("ms", 1);
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
                units = units || "ms";
                return +this.clone().startOf(units) === +makeAs(input, this).startOf(units);
            },
            min: function(other) {
                other = moment.apply(null, arguments);
                return other < this ? this : other;
            },
            max: function(other) {
                other = moment.apply(null, arguments);
                return other > this ? this : other;
            },
            zone: function(input, adjust) {
                adjust = adjust == null ? true : false;
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
                    if (offset !== input && adjust) {
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
            parseZone: function() {
                if (this._tzm) {
                    this.zone(this._tzm);
                } else if (typeof this._i === "string") {
                    this.zone(this._i);
                }
                return this;
            },
            hasAlignedHourOffset: function(input) {
                if (!input) {
                    input = 0;
                } else {
                    input = moment(input).zone();
                }
                return (this.zone() - input) % 60 === 0;
            },
            daysInMonth: function() {
                return daysInMonth(this.year(), this.month());
            },
            dayOfYear: function(input) {
                var dayOfYear = round((moment(this).startOf("day") - moment(this).startOf("year")) / 864e5) + 1;
                return input == null ? dayOfYear : this.add("d", input - dayOfYear);
            },
            quarter: function() {
                return Math.ceil((this.month() + 1) / 3);
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
                var weekday = (this.day() + 7 - this.lang()._week.dow) % 7;
                return input == null ? weekday : this.add("d", input - weekday);
            },
            isoWeekday: function(input) {
                // behaves the same as moment#day except
                // as a getter, returns 7 instead of 0 (1-7 range instead of 0-6)
                // as a setter, sunday should belong to the previous week.
                return input == null ? this.day() || 7 : this.day(this.day() % 7 ? input : input - 7);
            },
            isoWeeksInYear: function() {
                return weeksInYear(this.year(), 1, 4);
            },
            weeksInYear: function() {
                var weekInfo = this._lang._week;
                return weeksInYear(this.year(), weekInfo.dow, weekInfo.doy);
            },
            get: function(units) {
                units = normalizeUnits(units);
                return this[units]();
            },
            set: function(units, value) {
                units = normalizeUnits(units);
                if (typeof this[units] === "function") {
                    this[units](value);
                }
                return this;
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
        });
        // helper for adding shortcuts
        function makeGetterAndSetter(name, key) {
            // ignoreOffsetTransitions provides a hint to updateOffset to not
            // change hours/minutes when crossing a tz boundary.  This is frequently
            // desirable when modifying part of an existing moment object directly.
            var defaultIgnoreOffsetTransitions = key === "date" || key === "month" || key === "year";
            moment.fn[name] = moment.fn[name + "s"] = function(input, ignoreOffsetTransitions) {
                var utc = this._isUTC ? "UTC" : "";
                if (ignoreOffsetTransitions == null) {
                    ignoreOffsetTransitions = defaultIgnoreOffsetTransitions;
                }
                if (input != null) {
                    this._d["set" + utc + key](input);
                    moment.updateOffset(this, ignoreOffsetTransitions);
                    return this;
                } else {
                    return this._d["get" + utc + key]();
                }
            };
        }
        // loop through and add shortcuts (Date, Hours, Minutes, Seconds, Milliseconds)
        // Month has a custom getter/setter.
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
        extend(moment.duration.fn = Duration.prototype, {
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
                return this._milliseconds + this._days * 864e5 + this._months % 12 * 2592e6 + toInt(this._months / 12) * 31536e6;
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
            lang: moment.fn.lang,
            toIsoString: function() {
                // inspired by https://github.com/dordille/moment-isoduration/blob/master/moment.isoduration.js
                var years = Math.abs(this.years()), months = Math.abs(this.months()), days = Math.abs(this.days()), hours = Math.abs(this.hours()), minutes = Math.abs(this.minutes()), seconds = Math.abs(this.seconds() + this.milliseconds() / 1e3);
                if (!this.asSeconds()) {
                    // this is the same as C#'s (Noda) and python (isodate)...
                    // but not other JS (goog.date)
                    return "P0D";
                }
                return (this.asSeconds() < 0 ? "-" : "") + "P" + (years ? years + "Y" : "") + (months ? months + "M" : "") + (days ? days + "D" : "") + (hours || minutes || seconds ? "T" : "") + (hours ? hours + "H" : "") + (minutes ? minutes + "M" : "") + (seconds ? seconds + "S" : "");
            }
        });
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
                var b = number % 10, output = toInt(number % 100 / 10) === 1 ? "th" : b === 1 ? "st" : b === 2 ? "nd" : b === 3 ? "rd" : "th";
                return number + output;
            }
        });
        /* EMBED_LANGUAGES */
        /************************************
        Exposing Moment
    ************************************/
        function makeGlobal(deprecate) {
            var warned = false, local_moment = moment;
            /*global ender:false */
            if (typeof ender !== "undefined") {
                return;
            }
            // here, `this` means `window` in the browser, or `global` on the server
            // add `moment` as a global object via a string identifier,
            // for Closure Compiler "advanced" mode
            if (deprecate) {
                global.moment = function() {
                    if (!warned && console && console.warn) {
                        warned = true;
                        console.warn("Accessing Moment through the global scope is " + "deprecated, and will be removed in an upcoming " + "release.");
                    }
                    return local_moment.apply(null, arguments);
                };
                extend(global.moment, local_moment);
            } else {
                global["moment"] = moment;
            }
        }
        // CommonJS module is defined
        if (hasModule) {
            module.exports = moment;
        } else if (typeof define === "function" && define.amd) {
            define("moment", function(require, exports, module) {
                if (module.config && module.config() && module.config().noGlobal !== true) {
                    // If user provided noGlobal, he is aware of global
                    makeGlobal(module.config().noGlobal === undefined);
                }
                return moment;
            });
        } else {
            makeGlobal();
        }
    }).call(this);
});

define("ikj/datetimepicker2/3.0.0/datetimepicker.zh-CN-debug", [ "ikj/datetimepicker2/3.0.0/moment-debug" ], function(require, exports, module) {
    "use strict";
    var moment = require("ikj/datetimepicker2/3.0.0/moment-debug");
    // moment.js language configuration
    // language : chinese
    // author : suupic : https://github.com/suupic
    // author : Zeno Zeng : https://github.com/zenozeng
    (function(factory) {
        if (typeof define === "function" && define.amd) {
            define([ "moment" ], factory);
        } else if (typeof exports === "object") {
            factory(moment);
            // seajs
            module.exports = moment;
        } else {
            factory(moment);
        }
    })(function(moment) {
        return moment.lang("zh-cn", {
            months: "一月_二月_三月_四月_五月_六月_七月_八月_九月_十月_十一月_十二月".split("_"),
            monthsShort: "1月_2月_3月_4月_5月_6月_7月_8月_9月_10月_11月_12月".split("_"),
            weekdays: "星期日_星期一_星期二_星期三_星期四_星期五_星期六".split("_"),
            weekdaysShort: "周日_周一_周二_周三_周四_周五_周六".split("_"),
            weekdaysMin: "日_一_二_三_四_五_六".split("_"),
            longDateFormat: {
                LT: "Ah点mm",
                L: "YYYY年MMMD日",
                LL: "YYYY年MMMD日",
                LLL: "YYYY年MMMD日LT",
                LLLL: "YYYY年MMMD日ddddLT",
                l: "YYYY年MMMD日",
                ll: "YYYY年MMMD日",
                lll: "YYYY年MMMD日LT",
                llll: "YYYY年MMMD日ddddLT"
            },
            meridiem: function(hour, minute, isLower) {
                var hm = hour * 100 + minute;
                if (hm < 600) {
                    return "凌晨";
                } else if (hm < 900) {
                    return "早上";
                } else if (hm < 1130) {
                    return "上午";
                } else if (hm < 1230) {
                    return "中午";
                } else if (hm < 1800) {
                    return "下午";
                } else {
                    return "晚上";
                }
            },
            calendar: {
                sameDay: function() {
                    return this.minutes() === 0 ? "[今天]Ah[点整]" : "[今天]LT";
                },
                nextDay: function() {
                    return this.minutes() === 0 ? "[明天]Ah[点整]" : "[明天]LT";
                },
                lastDay: function() {
                    return this.minutes() === 0 ? "[昨天]Ah[点整]" : "[昨天]LT";
                },
                nextWeek: function() {
                    var startOfWeek, prefix;
                    startOfWeek = moment().startOf("week");
                    prefix = this.unix() - startOfWeek.unix() >= 7 * 24 * 3600 ? "[下]" : "[本]";
                    return this.minutes() === 0 ? prefix + "dddAh点整" : prefix + "dddAh点mm";
                },
                lastWeek: function() {
                    var startOfWeek, prefix;
                    startOfWeek = moment().startOf("week");
                    prefix = this.unix() < startOfWeek.unix() ? "[上]" : "[本]";
                    return this.minutes() === 0 ? prefix + "dddAh点整" : prefix + "dddAh点mm";
                },
                sameElse: "L"
            },
            ordinal: function(number, period) {
                switch (period) {
                  case "d":
                  case "D":
                  case "DDD":
                    return number + "日";

                  case "M":
                    return number + "月";

                  case "w":
                  case "W":
                    return number + "周";

                  default:
                    return number;
                }
            },
            relativeTime: {
                future: "%s内",
                past: "%s前",
                s: "几秒",
                m: "1分钟",
                mm: "%d分钟",
                h: "1小时",
                hh: "%d小时",
                d: "1天",
                dd: "%d天",
                M: "1个月",
                MM: "%d个月",
                y: "1年",
                yy: "%d年"
            },
            week: {
                // GB/T 7408-1994《数据元和交换格式·信息交换·日期和时间表示法》与ISO 8601:1988等效
                dow: 1,
                // Monday is the first day of the week.
                doy: 4
            }
        });
    });
});

define("ikj/datetimepicker2/3.0.0/datetimepicker-debug.css", [], function() {
    seajs.importStyle(".bootstrap-datetimepicker-widget{top:0;left:0;width:250px;padding:4px;margin-top:1px;z-index:99999!important;border-radius:4px}.bootstrap-datetimepicker-widget.timepicker-sbs{width:600px}.bootstrap-datetimepicker-widget.bottom:before{content:'';display:inline-block;border-left:7px solid transparent;border-right:7px solid transparent;border-bottom:7px solid #ccc;border-bottom-color:rgba(0,0,0,.2);position:absolute;top:-7px;left:7px}.bootstrap-datetimepicker-widget.bottom:after{content:'';display:inline-block;border-left:6px solid transparent;border-right:6px solid transparent;border-bottom:6px solid #fff;position:absolute;top:-6px;left:8px}.bootstrap-datetimepicker-widget.top:before{content:'';display:inline-block;border-left:7px solid transparent;border-right:7px solid transparent;border-top:7px solid #ccc;border-top-color:rgba(0,0,0,.2);position:absolute;bottom:-7px;left:6px}.bootstrap-datetimepicker-widget.top:after{content:'';display:inline-block;border-left:6px solid transparent;border-right:6px solid transparent;border-top:6px solid #fff;position:absolute;bottom:-6px;left:7px}.bootstrap-datetimepicker-widget .dow{width:14.2857%}.bootstrap-datetimepicker-widget.pull-right:before{left:auto;right:6px}.bootstrap-datetimepicker-widget.pull-right:after{left:auto;right:7px}.bootstrap-datetimepicker-widget>ul{list-style-type:none;margin:0}.bootstrap-datetimepicker-widget .timepicker-hour,.bootstrap-datetimepicker-widget .timepicker-minute,.bootstrap-datetimepicker-widget .timepicker-second{width:100%;font-weight:700;font-size:1.2em}.bootstrap-datetimepicker-widget table[data-hour-format=\"12\"] .separator{width:4px;padding:0;margin:0}.bootstrap-datetimepicker-widget .datepicker>div{display:none}.bootstrap-datetimepicker-widget .picker-switch{text-align:center}.bootstrap-datetimepicker-widget table{width:100%;margin:0}.bootstrap-datetimepicker-widget td,.bootstrap-datetimepicker-widget th{text-align:center;width:20px;height:20px;border-radius:4px}.bootstrap-datetimepicker-widget td.day:hover,.bootstrap-datetimepicker-widget td.hour:hover,.bootstrap-datetimepicker-widget td.minute:hover,.bootstrap-datetimepicker-widget td.second:hover{background:#eee;cursor:pointer}.bootstrap-datetimepicker-widget td.old,.bootstrap-datetimepicker-widget td.new{color:#999}.bootstrap-datetimepicker-widget td.today{position:relative}.bootstrap-datetimepicker-widget td.today:before{content:'';display:inline-block;border-left:7px solid transparent;border-bottom:7px solid #428bca;border-top-color:rgba(0,0,0,.2);position:absolute;bottom:4px;right:4px}.bootstrap-datetimepicker-widget td.active,.bootstrap-datetimepicker-widget td.active:hover{background-color:#428bca;color:#fff;text-shadow:0 -1px 0 rgba(0,0,0,.25)}.bootstrap-datetimepicker-widget td.active.today:before{border-bottom-color:#fff}.bootstrap-datetimepicker-widget td.disabled,.bootstrap-datetimepicker-widget td.disabled:hover{background:0;color:#999;cursor:not-allowed}.bootstrap-datetimepicker-widget td span{display:block;width:47px;height:54px;line-height:54px;float:left;margin:2px;cursor:pointer;border-radius:4px}.bootstrap-datetimepicker-widget td span:hover{background:#eee}.bootstrap-datetimepicker-widget td span.active{background-color:#428bca;color:#fff;text-shadow:0 -1px 0 rgba(0,0,0,.25)}.bootstrap-datetimepicker-widget td span.old{color:#999}.bootstrap-datetimepicker-widget td span.disabled,.bootstrap-datetimepicker-widget td span.disabled:hover{background:0;color:#999;cursor:not-allowed}.bootstrap-datetimepicker-widget th.switch{width:145px}.bootstrap-datetimepicker-widget th.next,.bootstrap-datetimepicker-widget th.prev{font-size:21px}.bootstrap-datetimepicker-widget th.disabled,.bootstrap-datetimepicker-widget th.disabled:hover{background:0;color:#999;cursor:not-allowed}.bootstrap-datetimepicker-widget thead tr:first-child th{cursor:pointer}.bootstrap-datetimepicker-widget thead tr:first-child th:hover{background:#eee}.input-group.date .input-group-addon span{display:block;cursor:pointer;width:16px;height:16px}.bootstrap-datetimepicker-widget.left-oriented:before{left:auto;right:6px}.bootstrap-datetimepicker-widget.left-oriented:after{left:auto;right:7px}.bootstrap-datetimepicker-widget ul.list-unstyled li div.timepicker div.timepicker-picker table.table-condensed tbody>tr>td{padding:0!important}.bootstrap-datetimepicker-widget .btn{padding:4px 0;background:#fff!important;color:#333!important}.bootstrap-datetimepicker-widget .btn:hover{background:#eee!important}");
});