<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/5/23
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .icons h1, h2, h3, p, div, footer {
        font-family: "Helvetica Neue", Arial, sans-serif;
    }

    .icons h1 {
        padding: 20px 20px 16px 20px;
        font-size: 26px;
        line-height: 26px;
        font-weight: normal;
        color: #FFF;
        background-color: #2196F3;
    }

    .icons h1 svg {
        vertical-align: middle;
        width: 26px;
        height: 26px;
        margin: 0 6px 4px 0;
    }

    .icons h1 svg path {
        fill: #FFF;
    }

    .icons h1 .version {
        font-size: 14px;
        background: #FFF;
        padding: 4px 10px;
        float: right;
        border-radius: 2px;
        margin: -3px 0 0 0;
        color: #666;
        font-weight: bold;
    }

    .icons h1 .version::before {
        content: 'v';
    }

    .icons h2 {
        font-size: 18px;
        padding: 20px;
    }

    .icons h3 {
        font-size: 14px;
        padding: 10px 20px 0 20px;
        font-weight: bold;
    }

    .icons p {
        padding: 10px 20px;
    }

    .icons p code {
        display: inline-block;
        vertical-align: middle;
        background: #F1F1F1;
        padding: 3px 5px;
        border-radius: 3px;
        border: 1px solid #DDD;
    }

    .icons p i.mdi {
        vertical-align: middle;
        border-radius: 4px;
        display: inline-block;
    }

    .icons p i.mdi.dark-demo {
        background: #333;
    }

    .icons p.note {
        color: #999;
        font-size: 14px;
        padding: 0 20px 5px 20px;
    }

    .icons {
        padding: 0 20px 10px 20px;
        -webkit-column-count: 3;
        -moz-column-count: 3;
        column-count: 3;
        -webkit-column-gap: 20px;
        -moz-column-gap: 20px;
        column-gap: 20px;
    }

    .icons div {
        line-height: 3em;
    }

    .icons div span {
        cursor: pointer;
        font-size: 14px;
        text-overflow: ellipsis;
        display: inline-block;
        max-width: calc(100% - 90px);
        overflow: hidden;
        vertical-align: middle;
        white-space: nowrap;
    }

    .icons div code:hover,
    .icons div span:hover,
    .icons div i:hover {
        color: #3c90be;
    }

    .icons div code:hover {
        border-color: #3c90be;
    }

    .icons div code {
        border: 1px solid #DDD;
        width: 46px;
        margin-left: 2px;
        margin-right: 4px;
        border-radius: 4px;
        display: inline-block;
        vertical-align: middle;
        text-align: center;
        line-height: 24px;
        cursor: pointer;
    }

    .icons div i {
        display: inline-block;
        width: 32px;
        height: 24px;
        text-align: center;
        vertical-align: middle;
        cursor: pointer;
        line-height: 24px;
    }

    .icons .mdi:before {
        font-size: 24px;
    }

    pre {
        margin: 0 20px;
        font-family: Consolas, monospace;
        padding: 10px;
        border: 1px solid #DDD;
        background: #F1F1F1;
    }

    div.copied {
        position: fixed;
        top: 100px;
        left: 50%;
        width: 200px;
        text-align: center;
        color: #3c763d;
        background-color: #dff0d8;
        border: 1px solid #d6e9c6;
        padding: 10px 15px;
        border-radius: 4px;
        margin-left: -100px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    }

    footer {
        padding: 20px;
        color: #666;
        border-top: 1px solid #DDD;
        background: #F1F1F1;
    }

    footer a {
        color: #e91e63;
        text-decoration: none;
    }
</style>

<div class="icons" id="icons"></div>

<script type="text/javascript">
    (function () {
        var icons = [{"name": "access-point", "hex": "f002"}, {
            "name": "access-point-network",
            "hex": "f003"
        }, {"name": "account", "hex": "f004"}, {"name": "account-alert", "hex": "f005"}, {
            "name": "account-box",
            "hex": "f006"
        }, {"name": "account-box-outline", "hex": "f007"}, {
            "name": "account-card-details",
            "hex": "f5d2"
        }, {"name": "account-check", "hex": "f008"}, {
            "name": "account-circle",
            "hex": "f009"
        }, {"name": "account-convert", "hex": "f00a"}, {
            "name": "account-edit",
            "hex": "f6bb"
        }, {"name": "account-group", "hex": "f848"}, {
            "name": "account-key",
            "hex": "f00b"
        }, {"name": "account-location", "hex": "f00c"}, {
            "name": "account-minus",
            "hex": "f00d"
        }, {"name": "account-multiple", "hex": "f00e"}, {
            "name": "account-multiple-minus",
            "hex": "f5d3"
        }, {"name": "account-multiple-outline", "hex": "f00f"}, {
            "name": "account-multiple-plus",
            "hex": "f010"
        }, {"name": "account-multiple-plus-outline", "hex": "f7ff"}, {
            "name": "account-network",
            "hex": "f011"
        }, {"name": "account-off", "hex": "f012"}, {"name": "account-outline", "hex": "f013"}, {
            "name": "account-plus",
            "hex": "f014"
        }, {"name": "account-plus-outline", "hex": "f800"}, {
            "name": "account-remove",
            "hex": "f015"
        }, {"name": "account-search", "hex": "f016"}, {
            "name": "account-settings",
            "hex": "f630"
        }, {"name": "account-settings-variant", "hex": "f631"}, {
            "name": "account-star",
            "hex": "f017"
        }, {"name": "account-switch", "hex": "f019"}, {"name": "accusoft", "hex": "f849"}, {
            "name": "adjust",
            "hex": "f01a"
        }, {"name": "air-conditioner", "hex": "f01b"}, {"name": "airballoon", "hex": "f01c"}, {
            "name": "airplane",
            "hex": "f01d"
        }, {"name": "airplane-landing", "hex": "f5d4"}, {
            "name": "airplane-off",
            "hex": "f01e"
        }, {"name": "airplane-takeoff", "hex": "f5d5"}, {"name": "airplay", "hex": "f01f"}, {
            "name": "airport",
            "hex": "f84a"
        }, {"name": "alarm", "hex": "f020"}, {"name": "alarm-bell", "hex": "f78d"}, {
            "name": "alarm-check",
            "hex": "f021"
        }, {"name": "alarm-light", "hex": "f78e"}, {"name": "alarm-multiple", "hex": "f022"}, {
            "name": "alarm-off",
            "hex": "f023"
        }, {"name": "alarm-plus", "hex": "f024"}, {"name": "alarm-snooze", "hex": "f68d"}, {
            "name": "album",
            "hex": "f025"
        }, {"name": "alert", "hex": "f026"}, {"name": "alert-box", "hex": "f027"}, {
            "name": "alert-circle",
            "hex": "f028"
        }, {"name": "alert-circle-outline", "hex": "f5d6"}, {
            "name": "alert-decagram",
            "hex": "f6bc"
        }, {"name": "alert-octagon", "hex": "f029"}, {
            "name": "alert-octagram",
            "hex": "f766"
        }, {"name": "alert-outline", "hex": "f02a"}, {"name": "all-inclusive", "hex": "f6bd"}, {
            "name": "allo",
            "hex": "f801"
        }, {"name": "alpha", "hex": "f02b"}, {"name": "alphabetical", "hex": "f02c"}, {
            "name": "altimeter",
            "hex": "f5d7"
        }, {"name": "amazon", "hex": "f02d"}, {"name": "amazon-clouddrive", "hex": "f02e"}, {
            "name": "ambulance",
            "hex": "f02f"
        }, {"name": "amplifier", "hex": "f030"}, {"name": "anchor", "hex": "f031"}, {
            "name": "android",
            "hex": "f032"
        }, {"name": "android-debug-bridge", "hex": "f033"}, {
            "name": "android-head",
            "hex": "f78f"
        }, {"name": "android-studio", "hex": "f034"}, {"name": "angular", "hex": "f6b1"}, {
            "name": "angularjs",
            "hex": "f6be"
        }, {"name": "animation", "hex": "f5d8"}, {"name": "apple", "hex": "f035"}, {
            "name": "apple-finder",
            "hex": "f036"
        }, {"name": "apple-ios", "hex": "f037"}, {
            "name": "apple-keyboard-caps",
            "hex": "f632"
        }, {"name": "apple-keyboard-command", "hex": "f633"}, {
            "name": "apple-keyboard-control",
            "hex": "f634"
        }, {"name": "apple-keyboard-option", "hex": "f635"}, {
            "name": "apple-keyboard-shift",
            "hex": "f636"
        }, {"name": "apple-mobileme", "hex": "f038"}, {"name": "apple-safari", "hex": "f039"}, {
            "name": "application",
            "hex": "f614"
        }, {"name": "approval", "hex": "f790"}, {"name": "apps", "hex": "f03b"}, {
            "name": "archive",
            "hex": "f03c"
        }, {"name": "arrange-bring-forward", "hex": "f03d"}, {
            "name": "arrange-bring-to-front",
            "hex": "f03e"
        }, {"name": "arrange-send-backward", "hex": "f03f"}, {
            "name": "arrange-send-to-back",
            "hex": "f040"
        }, {"name": "arrow-all", "hex": "f041"}, {
            "name": "arrow-bottom-left",
            "hex": "f042"
        }, {"name": "arrow-bottom-right", "hex": "f043"}, {
            "name": "arrow-collapse",
            "hex": "f615"
        }, {"name": "arrow-collapse-all", "hex": "f044"}, {
            "name": "arrow-collapse-down",
            "hex": "f791"
        }, {"name": "arrow-collapse-horizontal", "hex": "f84b"}, {
            "name": "arrow-collapse-left",
            "hex": "f792"
        }, {"name": "arrow-collapse-right", "hex": "f793"}, {
            "name": "arrow-collapse-up",
            "hex": "f794"
        }, {"name": "arrow-collapse-vertical", "hex": "f84c"}, {
            "name": "arrow-down",
            "hex": "f045"
        }, {"name": "arrow-down-bold", "hex": "f72d"}, {
            "name": "arrow-down-bold-box",
            "hex": "f72e"
        }, {"name": "arrow-down-bold-box-outline", "hex": "f72f"}, {
            "name": "arrow-down-bold-circle",
            "hex": "f047"
        }, {"name": "arrow-down-bold-circle-outline", "hex": "f048"}, {
            "name": "arrow-down-bold-hexagon-outline",
            "hex": "f049"
        }, {"name": "arrow-down-box", "hex": "f6bf"}, {
            "name": "arrow-down-drop-circle",
            "hex": "f04a"
        }, {"name": "arrow-down-drop-circle-outline", "hex": "f04b"}, {
            "name": "arrow-down-thick",
            "hex": "f046"
        }, {"name": "arrow-expand", "hex": "f616"}, {
            "name": "arrow-expand-all",
            "hex": "f04c"
        }, {"name": "arrow-expand-down", "hex": "f795"}, {
            "name": "arrow-expand-horizontal",
            "hex": "f84d"
        }, {"name": "arrow-expand-left", "hex": "f796"}, {
            "name": "arrow-expand-right",
            "hex": "f797"
        }, {"name": "arrow-expand-up", "hex": "f798"}, {
            "name": "arrow-expand-vertical",
            "hex": "f84e"
        }, {"name": "arrow-left", "hex": "f04d"}, {
            "name": "arrow-left-bold",
            "hex": "f730"
        }, {"name": "arrow-left-bold-box", "hex": "f731"}, {
            "name": "arrow-left-bold-box-outline",
            "hex": "f732"
        }, {"name": "arrow-left-bold-circle", "hex": "f04f"}, {
            "name": "arrow-left-bold-circle-outline",
            "hex": "f050"
        }, {"name": "arrow-left-bold-hexagon-outline", "hex": "f051"}, {
            "name": "arrow-left-box",
            "hex": "f6c0"
        }, {"name": "arrow-left-drop-circle", "hex": "f052"}, {
            "name": "arrow-left-drop-circle-outline",
            "hex": "f053"
        }, {"name": "arrow-left-thick", "hex": "f04e"}, {
            "name": "arrow-right",
            "hex": "f054"
        }, {"name": "arrow-right-bold", "hex": "f733"}, {
            "name": "arrow-right-bold-box",
            "hex": "f734"
        }, {"name": "arrow-right-bold-box-outline", "hex": "f735"}, {
            "name": "arrow-right-bold-circle",
            "hex": "f056"
        }, {"name": "arrow-right-bold-circle-outline", "hex": "f057"}, {
            "name": "arrow-right-bold-hexagon-outline",
            "hex": "f058"
        }, {"name": "arrow-right-box", "hex": "f6c1"}, {
            "name": "arrow-right-drop-circle",
            "hex": "f059"
        }, {"name": "arrow-right-drop-circle-outline", "hex": "f05a"}, {
            "name": "arrow-right-thick",
            "hex": "f055"
        }, {"name": "arrow-top-left", "hex": "f05b"}, {"name": "arrow-top-right", "hex": "f05c"}, {
            "name": "arrow-up",
            "hex": "f05d"
        }, {"name": "arrow-up-bold", "hex": "f736"}, {
            "name": "arrow-up-bold-box",
            "hex": "f737"
        }, {"name": "arrow-up-bold-box-outline", "hex": "f738"}, {
            "name": "arrow-up-bold-circle",
            "hex": "f05f"
        }, {"name": "arrow-up-bold-circle-outline", "hex": "f060"}, {
            "name": "arrow-up-bold-hexagon-outline",
            "hex": "f061"
        }, {"name": "arrow-up-box", "hex": "f6c2"}, {
            "name": "arrow-up-drop-circle",
            "hex": "f062"
        }, {"name": "arrow-up-drop-circle-outline", "hex": "f063"}, {
            "name": "arrow-up-thick",
            "hex": "f05e"
        }, {"name": "artist", "hex": "f802"}, {"name": "assistant", "hex": "f064"}, {
            "name": "asterisk",
            "hex": "f6c3"
        }, {"name": "at", "hex": "f065"}, {"name": "atlassian", "hex": "f803"}, {
            "name": "atom",
            "hex": "f767"
        }, {"name": "attachment", "hex": "f066"}, {"name": "audiobook", "hex": "f067"}, {
            "name": "augmented-reality",
            "hex": "f84f"
        }, {"name": "auto-fix", "hex": "f068"}, {"name": "auto-upload", "hex": "f069"}, {
            "name": "autorenew",
            "hex": "f06a"
        }, {"name": "av-timer", "hex": "f06b"}, {"name": "azure", "hex": "f804"}, {
            "name": "baby",
            "hex": "f06c"
        }, {"name": "baby-buggy", "hex": "f68e"}, {"name": "backburger", "hex": "f06d"}, {
            "name": "backspace",
            "hex": "f06e"
        }, {"name": "backup-restore", "hex": "f06f"}, {"name": "badminton", "hex": "f850"}, {
            "name": "bandcamp",
            "hex": "f674"
        }, {"name": "bank", "hex": "f070"}, {"name": "barcode", "hex": "f071"}, {
            "name": "barcode-scan",
            "hex": "f072"
        }, {"name": "barley", "hex": "f073"}, {"name": "barrel", "hex": "f074"}, {
            "name": "baseball",
            "hex": "f851"
        }, {"name": "baseball-bat", "hex": "f852"}, {"name": "basecamp", "hex": "f075"}, {
            "name": "basket",
            "hex": "f076"
        }, {"name": "basket-fill", "hex": "f077"}, {"name": "basket-unfill", "hex": "f078"}, {
            "name": "basketball",
            "hex": "f805"
        }, {"name": "battery", "hex": "f079"}, {"name": "battery-10", "hex": "f07a"}, {
            "name": "battery-20",
            "hex": "f07b"
        }, {"name": "battery-30", "hex": "f07c"}, {"name": "battery-40", "hex": "f07d"}, {
            "name": "battery-50",
            "hex": "f07e"
        }, {"name": "battery-60", "hex": "f07f"}, {"name": "battery-70", "hex": "f080"}, {
            "name": "battery-80",
            "hex": "f081"
        }, {"name": "battery-90", "hex": "f082"}, {"name": "battery-alert", "hex": "f083"}, {
            "name": "battery-charging",
            "hex": "f084"
        }, {"name": "battery-charging-100", "hex": "f085"}, {
            "name": "battery-charging-20",
            "hex": "f086"
        }, {"name": "battery-charging-30", "hex": "f087"}, {
            "name": "battery-charging-40",
            "hex": "f088"
        }, {"name": "battery-charging-60", "hex": "f089"}, {
            "name": "battery-charging-80",
            "hex": "f08a"
        }, {"name": "battery-charging-90", "hex": "f08b"}, {
            "name": "battery-charging-wireless",
            "hex": "f806"
        }, {"name": "battery-charging-wireless-10", "hex": "f807"}, {
            "name": "battery-charging-wireless-20",
            "hex": "f808"
        }, {"name": "battery-charging-wireless-30", "hex": "f809"}, {
            "name": "battery-charging-wireless-40",
            "hex": "f80a"
        }, {"name": "battery-charging-wireless-50", "hex": "f80b"}, {
            "name": "battery-charging-wireless-60",
            "hex": "f80c"
        }, {"name": "battery-charging-wireless-70", "hex": "f80d"}, {
            "name": "battery-charging-wireless-80",
            "hex": "f80e"
        }, {"name": "battery-charging-wireless-90", "hex": "f80f"}, {
            "name": "battery-charging-wireless-alert",
            "hex": "f810"
        }, {"name": "battery-charging-wireless-outline", "hex": "f811"}, {
            "name": "battery-minus",
            "hex": "f08c"
        }, {"name": "battery-negative", "hex": "f08d"}, {
            "name": "battery-outline",
            "hex": "f08e"
        }, {"name": "battery-plus", "hex": "f08f"}, {
            "name": "battery-positive",
            "hex": "f090"
        }, {"name": "battery-unknown", "hex": "f091"}, {"name": "beach", "hex": "f092"}, {
            "name": "beaker",
            "hex": "f68f"
        }, {"name": "beats", "hex": "f097"}, {"name": "beer", "hex": "f098"}, {
            "name": "behance",
            "hex": "f099"
        }, {"name": "bell", "hex": "f09a"}, {"name": "bell-off", "hex": "f09b"}, {
            "name": "bell-outline",
            "hex": "f09c"
        }, {"name": "bell-plus", "hex": "f09d"}, {"name": "bell-ring", "hex": "f09e"}, {
            "name": "bell-ring-outline",
            "hex": "f09f"
        }, {"name": "bell-sleep", "hex": "f0a0"}, {"name": "beta", "hex": "f0a1"}, {
            "name": "bible",
            "hex": "f0a2"
        }, {"name": "bike", "hex": "f0a3"}, {"name": "bing", "hex": "f0a4"}, {
            "name": "binoculars",
            "hex": "f0a5"
        }, {"name": "bio", "hex": "f0a6"}, {"name": "biohazard", "hex": "f0a7"}, {
            "name": "bitbucket",
            "hex": "f0a8"
        }, {"name": "bitcoin", "hex": "f812"}, {"name": "black-mesa", "hex": "f0a9"}, {
            "name": "blackberry",
            "hex": "f0aa"
        }, {"name": "blender", "hex": "f0ab"}, {"name": "blinds", "hex": "f0ac"}, {
            "name": "block-helper",
            "hex": "f0ad"
        }, {"name": "blogger", "hex": "f0ae"}, {"name": "bluetooth", "hex": "f0af"}, {
            "name": "bluetooth-audio",
            "hex": "f0b0"
        }, {"name": "bluetooth-connect", "hex": "f0b1"}, {
            "name": "bluetooth-off",
            "hex": "f0b2"
        }, {"name": "bluetooth-settings", "hex": "f0b3"}, {
            "name": "bluetooth-transfer",
            "hex": "f0b4"
        }, {"name": "blur", "hex": "f0b5"}, {"name": "blur-linear", "hex": "f0b6"}, {
            "name": "blur-off",
            "hex": "f0b7"
        }, {"name": "blur-radial", "hex": "f0b8"}, {"name": "bomb", "hex": "f690"}, {
            "name": "bomb-off",
            "hex": "f6c4"
        }, {"name": "bone", "hex": "f0b9"}, {"name": "book", "hex": "f0ba"}, {
            "name": "book-minus",
            "hex": "f5d9"
        }, {"name": "book-multiple", "hex": "f0bb"}, {
            "name": "book-multiple-variant",
            "hex": "f0bc"
        }, {"name": "book-open", "hex": "f0bd"}, {
            "name": "book-open-page-variant",
            "hex": "f5da"
        }, {"name": "book-open-variant", "hex": "f0be"}, {"name": "book-plus", "hex": "f5db"}, {
            "name": "book-secure",
            "hex": "f799"
        }, {"name": "book-unsecure", "hex": "f79a"}, {"name": "book-variant", "hex": "f0bf"}, {
            "name": "bookmark",
            "hex": "f0c0"
        }, {"name": "bookmark-check", "hex": "f0c1"}, {
            "name": "bookmark-music",
            "hex": "f0c2"
        }, {"name": "bookmark-outline", "hex": "f0c3"}, {
            "name": "bookmark-plus",
            "hex": "f0c5"
        }, {"name": "bookmark-plus-outline", "hex": "f0c4"}, {
            "name": "bookmark-remove",
            "hex": "f0c6"
        }, {"name": "boombox", "hex": "f5dc"}, {"name": "bootstrap", "hex": "f6c5"}, {
            "name": "border-all",
            "hex": "f0c7"
        }, {"name": "border-bottom", "hex": "f0c8"}, {
            "name": "border-color",
            "hex": "f0c9"
        }, {"name": "border-horizontal", "hex": "f0ca"}, {
            "name": "border-inside",
            "hex": "f0cb"
        }, {"name": "border-left", "hex": "f0cc"}, {"name": "border-none", "hex": "f0cd"}, {
            "name": "border-outside",
            "hex": "f0ce"
        }, {"name": "border-right", "hex": "f0cf"}, {"name": "border-style", "hex": "f0d0"}, {
            "name": "border-top",
            "hex": "f0d1"
        }, {"name": "border-vertical", "hex": "f0d2"}, {"name": "bottle-wine", "hex": "f853"}, {
            "name": "bow-tie",
            "hex": "f677"
        }, {"name": "bowl", "hex": "f617"}, {"name": "bowling", "hex": "f0d3"}, {
            "name": "box",
            "hex": "f0d4"
        }, {"name": "box-cutter", "hex": "f0d5"}, {"name": "box-shadow", "hex": "f637"}, {
            "name": "bridge",
            "hex": "f618"
        }, {"name": "briefcase", "hex": "f0d6"}, {
            "name": "briefcase-check",
            "hex": "f0d7"
        }, {"name": "briefcase-download", "hex": "f0d8"}, {
            "name": "briefcase-outline",
            "hex": "f813"
        }, {"name": "briefcase-upload", "hex": "f0d9"}, {
            "name": "brightness-1",
            "hex": "f0da"
        }, {"name": "brightness-2", "hex": "f0db"}, {"name": "brightness-3", "hex": "f0dc"}, {
            "name": "brightness-4",
            "hex": "f0dd"
        }, {"name": "brightness-5", "hex": "f0de"}, {"name": "brightness-6", "hex": "f0df"}, {
            "name": "brightness-7",
            "hex": "f0e0"
        }, {"name": "brightness-auto", "hex": "f0e1"}, {"name": "broom", "hex": "f0e2"}, {
            "name": "brush",
            "hex": "f0e3"
        }, {"name": "buffer", "hex": "f619"}, {"name": "bug", "hex": "f0e4"}, {
            "name": "bulletin-board",
            "hex": "f0e5"
        }, {"name": "bullhorn", "hex": "f0e6"}, {"name": "bullseye", "hex": "f5dd"}, {
            "name": "bus",
            "hex": "f0e7"
        }, {"name": "bus-articulated-end", "hex": "f79b"}, {
            "name": "bus-articulated-front",
            "hex": "f79c"
        }, {"name": "bus-double-decker", "hex": "f79d"}, {"name": "bus-school", "hex": "f79e"}, {
            "name": "bus-side",
            "hex": "f79f"
        }, {"name": "cached", "hex": "f0e8"}, {"name": "cake", "hex": "f0e9"}, {
            "name": "cake-layered",
            "hex": "f0ea"
        }, {"name": "cake-variant", "hex": "f0eb"}, {"name": "calculator", "hex": "f0ec"}, {
            "name": "calendar",
            "hex": "f0ed"
        }, {"name": "calendar-blank", "hex": "f0ee"}, {
            "name": "calendar-check",
            "hex": "f0ef"
        }, {"name": "calendar-clock", "hex": "f0f0"}, {
            "name": "calendar-multiple",
            "hex": "f0f1"
        }, {"name": "calendar-multiple-check", "hex": "f0f2"}, {
            "name": "calendar-plus",
            "hex": "f0f3"
        }, {"name": "calendar-question", "hex": "f691"}, {
            "name": "calendar-range",
            "hex": "f678"
        }, {"name": "calendar-remove", "hex": "f0f4"}, {
            "name": "calendar-text",
            "hex": "f0f5"
        }, {"name": "calendar-today", "hex": "f0f6"}, {"name": "call-made", "hex": "f0f7"}, {
            "name": "call-merge",
            "hex": "f0f8"
        }, {"name": "call-missed", "hex": "f0f9"}, {"name": "call-received", "hex": "f0fa"}, {
            "name": "call-split",
            "hex": "f0fb"
        }, {"name": "camcorder", "hex": "f0fc"}, {"name": "camcorder-box", "hex": "f0fd"}, {
            "name": "camcorder-box-off",
            "hex": "f0fe"
        }, {"name": "camcorder-off", "hex": "f0ff"}, {"name": "camera", "hex": "f100"}, {
            "name": "camera-burst",
            "hex": "f692"
        }, {"name": "camera-enhance", "hex": "f101"}, {
            "name": "camera-front",
            "hex": "f102"
        }, {"name": "camera-front-variant", "hex": "f103"}, {
            "name": "camera-gopro",
            "hex": "f7a0"
        }, {"name": "camera-iris", "hex": "f104"}, {
            "name": "camera-metering-center",
            "hex": "f7a1"
        }, {"name": "camera-metering-matrix", "hex": "f7a2"}, {
            "name": "camera-metering-partial",
            "hex": "f7a3"
        }, {"name": "camera-metering-spot", "hex": "f7a4"}, {
            "name": "camera-off",
            "hex": "f5df"
        }, {"name": "camera-party-mode", "hex": "f105"}, {
            "name": "camera-rear",
            "hex": "f106"
        }, {"name": "camera-rear-variant", "hex": "f107"}, {
            "name": "camera-switch",
            "hex": "f108"
        }, {"name": "camera-timer", "hex": "f109"}, {"name": "cancel", "hex": "f739"}, {
            "name": "candle",
            "hex": "f5e2"
        }, {"name": "candycane", "hex": "f10a"}, {"name": "cannabis", "hex": "f7a5"}, {
            "name": "car",
            "hex": "f10b"
        }, {"name": "car-battery", "hex": "f10c"}, {"name": "car-connected", "hex": "f10d"}, {
            "name": "car-convertible",
            "hex": "f7a6"
        }, {"name": "car-estate", "hex": "f7a7"}, {"name": "car-hatchback", "hex": "f7a8"}, {
            "name": "car-pickup",
            "hex": "f7a9"
        }, {"name": "car-side", "hex": "f7aa"}, {"name": "car-sports", "hex": "f7ab"}, {
            "name": "car-wash",
            "hex": "f10e"
        }, {"name": "caravan", "hex": "f7ac"}, {"name": "cards", "hex": "f638"}, {
            "name": "cards-outline",
            "hex": "f639"
        }, {"name": "cards-playing-outline", "hex": "f63a"}, {
            "name": "cards-variant",
            "hex": "f6c6"
        }, {"name": "carrot", "hex": "f10f"}, {"name": "cart", "hex": "f110"}, {
            "name": "cart-off",
            "hex": "f66b"
        }, {"name": "cart-outline", "hex": "f111"}, {"name": "cart-plus", "hex": "f112"}, {
            "name": "case-sensitive-alt",
            "hex": "f113"
        }, {"name": "cash", "hex": "f114"}, {"name": "cash-100", "hex": "f115"}, {
            "name": "cash-multiple",
            "hex": "f116"
        }, {"name": "cash-usd", "hex": "f117"}, {"name": "cast", "hex": "f118"}, {
            "name": "cast-connected",
            "hex": "f119"
        }, {"name": "cast-off", "hex": "f789"}, {"name": "castle", "hex": "f11a"}, {
            "name": "cat",
            "hex": "f11b"
        }, {"name": "cctv", "hex": "f7ad"}, {"name": "ceiling-light", "hex": "f768"}, {
            "name": "cellphone",
            "hex": "f11c"
        }, {"name": "cellphone-android", "hex": "f11d"}, {
            "name": "cellphone-basic",
            "hex": "f11e"
        }, {"name": "cellphone-dock", "hex": "f11f"}, {
            "name": "cellphone-iphone",
            "hex": "f120"
        }, {"name": "cellphone-link", "hex": "f121"}, {
            "name": "cellphone-link-off",
            "hex": "f122"
        }, {"name": "cellphone-settings", "hex": "f123"}, {
            "name": "cellphone-wireless",
            "hex": "f814"
        }, {"name": "certificate", "hex": "f124"}, {"name": "chair-school", "hex": "f125"}, {
            "name": "chart-arc",
            "hex": "f126"
        }, {"name": "chart-areaspline", "hex": "f127"}, {
            "name": "chart-bar",
            "hex": "f128"
        }, {"name": "chart-bar-stacked", "hex": "f769"}, {
            "name": "chart-bubble",
            "hex": "f5e3"
        }, {"name": "chart-donut", "hex": "f7ae"}, {
            "name": "chart-donut-variant",
            "hex": "f7af"
        }, {"name": "chart-gantt", "hex": "f66c"}, {"name": "chart-histogram", "hex": "f129"}, {
            "name": "chart-line",
            "hex": "f12a"
        }, {"name": "chart-line-stacked", "hex": "f76a"}, {
            "name": "chart-line-variant",
            "hex": "f7b0"
        }, {"name": "chart-pie", "hex": "f12b"}, {
            "name": "chart-scatterplot-hexbin",
            "hex": "f66d"
        }, {"name": "chart-timeline", "hex": "f66e"}, {"name": "check", "hex": "f12c"}, {
            "name": "check-all",
            "hex": "f12d"
        }, {"name": "check-circle", "hex": "f5e0"}, {
            "name": "check-circle-outline",
            "hex": "f5e1"
        }, {"name": "check-outline", "hex": "f854"}, {
            "name": "checkbox-blank",
            "hex": "f12e"
        }, {"name": "checkbox-blank-circle", "hex": "f12f"}, {
            "name": "checkbox-blank-circle-outline",
            "hex": "f130"
        }, {"name": "checkbox-blank-outline", "hex": "f131"}, {
            "name": "checkbox-intermediate",
            "hex": "f855"
        }, {"name": "checkbox-marked", "hex": "f132"}, {
            "name": "checkbox-marked-circle",
            "hex": "f133"
        }, {"name": "checkbox-marked-circle-outline", "hex": "f134"}, {
            "name": "checkbox-marked-outline",
            "hex": "f135"
        }, {"name": "checkbox-multiple-blank", "hex": "f136"}, {
            "name": "checkbox-multiple-blank-circle",
            "hex": "f63b"
        }, {
            "name": "checkbox-multiple-blank-circle-outline",
            "hex": "f63c"
        }, {"name": "checkbox-multiple-blank-outline", "hex": "f137"}, {
            "name": "checkbox-multiple-marked",
            "hex": "f138"
        }, {
            "name": "checkbox-multiple-marked-circle",
            "hex": "f63d"
        }, {
            "name": "checkbox-multiple-marked-circle-outline",
            "hex": "f63e"
        }, {"name": "checkbox-multiple-marked-outline", "hex": "f139"}, {
            "name": "checkerboard",
            "hex": "f13a"
        }, {"name": "chemical-weapon", "hex": "f13b"}, {"name": "chess-bishop", "hex": "f85b"}, {
            "name": "chess-king",
            "hex": "f856"
        }, {"name": "chess-knight", "hex": "f857"}, {"name": "chess-pawn", "hex": "f858"}, {
            "name": "chess-queen",
            "hex": "f859"
        }, {"name": "chess-rook", "hex": "f85a"}, {
            "name": "chevron-double-down",
            "hex": "f13c"
        }, {"name": "chevron-double-left", "hex": "f13d"}, {
            "name": "chevron-double-right",
            "hex": "f13e"
        }, {"name": "chevron-double-up", "hex": "f13f"}, {
            "name": "chevron-down",
            "hex": "f140"
        }, {"name": "chevron-left", "hex": "f141"}, {"name": "chevron-right", "hex": "f142"}, {
            "name": "chevron-up",
            "hex": "f143"
        }, {"name": "chili-hot", "hex": "f7b1"}, {"name": "chili-medium", "hex": "f7b2"}, {
            "name": "chili-mild",
            "hex": "f7b3"
        }, {"name": "chip", "hex": "f61a"}, {"name": "church", "hex": "f144"}, {
            "name": "circle",
            "hex": "f764"
        }, {"name": "circle-outline", "hex": "f765"}, {"name": "cisco-webex", "hex": "f145"}, {
            "name": "city",
            "hex": "f146"
        }, {"name": "clipboard", "hex": "f147"}, {
            "name": "clipboard-account",
            "hex": "f148"
        }, {"name": "clipboard-alert", "hex": "f149"}, {
            "name": "clipboard-arrow-down",
            "hex": "f14a"
        }, {"name": "clipboard-arrow-left", "hex": "f14b"}, {
            "name": "clipboard-check",
            "hex": "f14c"
        }, {"name": "clipboard-flow", "hex": "f6c7"}, {
            "name": "clipboard-outline",
            "hex": "f14d"
        }, {"name": "clipboard-plus", "hex": "f750"}, {
            "name": "clipboard-pulse",
            "hex": "f85c"
        }, {"name": "clipboard-pulse-outline", "hex": "f85d"}, {
            "name": "clipboard-text",
            "hex": "f14e"
        }, {"name": "clippy", "hex": "f14f"}, {"name": "clock", "hex": "f150"}, {
            "name": "clock-alert",
            "hex": "f5ce"
        }, {"name": "clock-end", "hex": "f151"}, {"name": "clock-fast", "hex": "f152"}, {
            "name": "clock-in",
            "hex": "f153"
        }, {"name": "clock-out", "hex": "f154"}, {"name": "clock-start", "hex": "f155"}, {
            "name": "close",
            "hex": "f156"
        }, {"name": "close-box", "hex": "f157"}, {"name": "close-box-outline", "hex": "f158"}, {
            "name": "close-circle",
            "hex": "f159"
        }, {"name": "close-circle-outline", "hex": "f15a"}, {
            "name": "close-network",
            "hex": "f15b"
        }, {"name": "close-octagon", "hex": "f15c"}, {
            "name": "close-octagon-outline",
            "hex": "f15d"
        }, {"name": "close-outline", "hex": "f6c8"}, {"name": "closed-caption", "hex": "f15e"}, {
            "name": "cloud",
            "hex": "f15f"
        }, {"name": "cloud-braces", "hex": "f7b4"}, {"name": "cloud-check", "hex": "f160"}, {
            "name": "cloud-circle",
            "hex": "f161"
        }, {"name": "cloud-download", "hex": "f162"}, {
            "name": "cloud-off-outline",
            "hex": "f164"
        }, {"name": "cloud-outline", "hex": "f163"}, {
            "name": "cloud-print",
            "hex": "f165"
        }, {"name": "cloud-print-outline", "hex": "f166"}, {"name": "cloud-sync", "hex": "f63f"}, {
            "name": "cloud-tags",
            "hex": "f7b5"
        }, {"name": "cloud-upload", "hex": "f167"}, {"name": "clover", "hex": "f815"}, {
            "name": "code-array",
            "hex": "f168"
        }, {"name": "code-braces", "hex": "f169"}, {"name": "code-brackets", "hex": "f16a"}, {
            "name": "code-equal",
            "hex": "f16b"
        }, {"name": "code-greater-than", "hex": "f16c"}, {
            "name": "code-greater-than-or-equal",
            "hex": "f16d"
        }, {"name": "code-less-than", "hex": "f16e"}, {
            "name": "code-less-than-or-equal",
            "hex": "f16f"
        }, {"name": "code-not-equal", "hex": "f170"}, {
            "name": "code-not-equal-variant",
            "hex": "f171"
        }, {"name": "code-parentheses", "hex": "f172"}, {"name": "code-string", "hex": "f173"}, {
            "name": "code-tags",
            "hex": "f174"
        }, {"name": "code-tags-check", "hex": "f693"}, {"name": "codepen", "hex": "f175"}, {
            "name": "coffee",
            "hex": "f176"
        }, {"name": "coffee-outline", "hex": "f6c9"}, {"name": "coffee-to-go", "hex": "f177"}, {
            "name": "coin",
            "hex": "f178"
        }, {"name": "coins", "hex": "f694"}, {"name": "collage", "hex": "f640"}, {
            "name": "color-helper",
            "hex": "f179"
        }, {"name": "comment", "hex": "f17a"}, {
            "name": "comment-account",
            "hex": "f17b"
        }, {"name": "comment-account-outline", "hex": "f17c"}, {
            "name": "comment-alert",
            "hex": "f17d"
        }, {"name": "comment-alert-outline", "hex": "f17e"}, {
            "name": "comment-check",
            "hex": "f17f"
        }, {"name": "comment-check-outline", "hex": "f180"}, {
            "name": "comment-multiple",
            "hex": "f85e"
        }, {"name": "comment-multiple-outline", "hex": "f181"}, {
            "name": "comment-outline",
            "hex": "f182"
        }, {"name": "comment-plus-outline", "hex": "f183"}, {
            "name": "comment-processing",
            "hex": "f184"
        }, {"name": "comment-processing-outline", "hex": "f185"}, {
            "name": "comment-question",
            "hex": "f816"
        }, {"name": "comment-question-outline", "hex": "f186"}, {
            "name": "comment-remove",
            "hex": "f5de"
        }, {"name": "comment-remove-outline", "hex": "f187"}, {
            "name": "comment-text",
            "hex": "f188"
        }, {"name": "comment-text-multiple", "hex": "f85f"}, {
            "name": "comment-text-multiple-outline",
            "hex": "f860"
        }, {"name": "comment-text-outline", "hex": "f189"}, {"name": "compare", "hex": "f18a"}, {
            "name": "compass",
            "hex": "f18b"
        }, {"name": "compass-outline", "hex": "f18c"}, {"name": "console", "hex": "f18d"}, {
            "name": "console-line",
            "hex": "f7b6"
        }, {"name": "contact-mail", "hex": "f18e"}, {"name": "contacts", "hex": "f6ca"}, {
            "name": "content-copy",
            "hex": "f18f"
        }, {"name": "content-cut", "hex": "f190"}, {
            "name": "content-duplicate",
            "hex": "f191"
        }, {"name": "content-paste", "hex": "f192"}, {
            "name": "content-save",
            "hex": "f193"
        }, {"name": "content-save-all", "hex": "f194"}, {
            "name": "content-save-outline",
            "hex": "f817"
        }, {"name": "content-save-settings", "hex": "f61b"}, {
            "name": "contrast",
            "hex": "f195"
        }, {"name": "contrast-box", "hex": "f196"}, {"name": "contrast-circle", "hex": "f197"}, {
            "name": "cookie",
            "hex": "f198"
        }, {"name": "copyright", "hex": "f5e6"}, {"name": "corn", "hex": "f7b7"}, {
            "name": "counter",
            "hex": "f199"
        }, {"name": "cow", "hex": "f19a"}, {"name": "crane", "hex": "f861"}, {
            "name": "creation",
            "hex": "f1c9"
        }, {"name": "credit-card", "hex": "f19b"}, {
            "name": "credit-card-multiple",
            "hex": "f19c"
        }, {"name": "credit-card-off", "hex": "f5e4"}, {
            "name": "credit-card-plus",
            "hex": "f675"
        }, {"name": "credit-card-scan", "hex": "f19d"}, {"name": "crop", "hex": "f19e"}, {
            "name": "crop-free",
            "hex": "f19f"
        }, {"name": "crop-landscape", "hex": "f1a0"}, {"name": "crop-portrait", "hex": "f1a1"}, {
            "name": "crop-rotate",
            "hex": "f695"
        }, {"name": "crop-square", "hex": "f1a2"}, {"name": "crosshairs", "hex": "f1a3"}, {
            "name": "crosshairs-gps",
            "hex": "f1a4"
        }, {"name": "crown", "hex": "f1a5"}, {"name": "cube", "hex": "f1a6"}, {
            "name": "cube-outline",
            "hex": "f1a7"
        }, {"name": "cube-send", "hex": "f1a8"}, {"name": "cube-unfolded", "hex": "f1a9"}, {
            "name": "cup",
            "hex": "f1aa"
        }, {"name": "cup-off", "hex": "f5e5"}, {"name": "cup-water", "hex": "f1ab"}, {
            "name": "curling",
            "hex": "f862"
        }, {"name": "currency-bdt", "hex": "f863"}, {"name": "currency-btc", "hex": "f1ac"}, {
            "name": "currency-chf",
            "hex": "f7b8"
        }, {"name": "currency-cny", "hex": "f7b9"}, {"name": "currency-eth", "hex": "f7ba"}, {
            "name": "currency-eur",
            "hex": "f1ad"
        }, {"name": "currency-gbp", "hex": "f1ae"}, {"name": "currency-inr", "hex": "f1af"}, {
            "name": "currency-jpy",
            "hex": "f7bb"
        }, {"name": "currency-krw", "hex": "f7bc"}, {"name": "currency-kzt", "hex": "f864"}, {
            "name": "currency-ngn",
            "hex": "f1b0"
        }, {"name": "currency-rub", "hex": "f1b1"}, {"name": "currency-sign", "hex": "f7bd"}, {
            "name": "currency-try",
            "hex": "f1b2"
        }, {"name": "currency-twd", "hex": "f7be"}, {
            "name": "currency-usd",
            "hex": "f1b3"
        }, {"name": "currency-usd-off", "hex": "f679"}, {
            "name": "cursor-default",
            "hex": "f1b4"
        }, {"name": "cursor-default-outline", "hex": "f1b5"}, {
            "name": "cursor-move",
            "hex": "f1b6"
        }, {"name": "cursor-pointer", "hex": "f1b7"}, {"name": "cursor-text", "hex": "f5e7"}, {
            "name": "database",
            "hex": "f1b8"
        }, {"name": "database-minus", "hex": "f1b9"}, {
            "name": "database-plus",
            "hex": "f1ba"
        }, {"name": "database-search", "hex": "f865"}, {
            "name": "debug-step-into",
            "hex": "f1bb"
        }, {"name": "debug-step-out", "hex": "f1bc"}, {"name": "debug-step-over", "hex": "f1bd"}, {
            "name": "decagram",
            "hex": "f76b"
        }, {"name": "decagram-outline", "hex": "f76c"}, {
            "name": "decimal-decrease",
            "hex": "f1be"
        }, {"name": "decimal-increase", "hex": "f1bf"}, {"name": "delete", "hex": "f1c0"}, {
            "name": "delete-circle",
            "hex": "f682"
        }, {"name": "delete-empty", "hex": "f6cb"}, {
            "name": "delete-forever",
            "hex": "f5e8"
        }, {"name": "delete-restore", "hex": "f818"}, {
            "name": "delete-sweep",
            "hex": "f5e9"
        }, {"name": "delete-variant", "hex": "f1c1"}, {"name": "delta", "hex": "f1c2"}, {
            "name": "deskphone",
            "hex": "f1c3"
        }, {"name": "desktop-classic", "hex": "f7bf"}, {"name": "desktop-mac", "hex": "f1c4"}, {
            "name": "desktop-tower",
            "hex": "f1c5"
        }, {"name": "details", "hex": "f1c6"}, {"name": "developer-board", "hex": "f696"}, {
            "name": "deviantart",
            "hex": "f1c7"
        }, {"name": "dialpad", "hex": "f61c"}, {"name": "diamond", "hex": "f1c8"}, {
            "name": "dice-1",
            "hex": "f1ca"
        }, {"name": "dice-2", "hex": "f1cb"}, {"name": "dice-3", "hex": "f1cc"}, {
            "name": "dice-4",
            "hex": "f1cd"
        }, {"name": "dice-5", "hex": "f1ce"}, {"name": "dice-6", "hex": "f1cf"}, {
            "name": "dice-d10",
            "hex": "f76e"
        }, {"name": "dice-d12", "hex": "f866"}, {"name": "dice-d20", "hex": "f5ea"}, {
            "name": "dice-d4",
            "hex": "f5eb"
        }, {"name": "dice-d6", "hex": "f5ec"}, {"name": "dice-d8", "hex": "f5ed"}, {
            "name": "dice-multiple",
            "hex": "f76d"
        }, {"name": "dictionary", "hex": "f61d"}, {"name": "dip-switch", "hex": "f7c0"}, {
            "name": "directions",
            "hex": "f1d0"
        }, {"name": "directions-fork", "hex": "f641"}, {"name": "discord", "hex": "f66f"}, {
            "name": "disk",
            "hex": "f5ee"
        }, {"name": "disk-alert", "hex": "f1d1"}, {"name": "disqus", "hex": "f1d2"}, {
            "name": "disqus-outline",
            "hex": "f1d3"
        }, {"name": "division", "hex": "f1d4"}, {"name": "division-box", "hex": "f1d5"}, {
            "name": "dna",
            "hex": "f683"
        }, {"name": "dns", "hex": "f1d6"}, {"name": "do-not-disturb", "hex": "f697"}, {
            "name": "do-not-disturb-off",
            "hex": "f698"
        }, {"name": "docker", "hex": "f867"}, {"name": "dolby", "hex": "f6b2"}, {
            "name": "domain",
            "hex": "f1d7"
        }, {"name": "donkey", "hex": "f7c1"}, {"name": "door", "hex": "f819"}, {
            "name": "door-closed",
            "hex": "f81a"
        }, {"name": "door-open", "hex": "f81b"}, {"name": "doorbell-video", "hex": "f868"}, {
            "name": "dots-horizontal",
            "hex": "f1d8"
        }, {"name": "dots-horizontal-circle", "hex": "f7c2"}, {
            "name": "dots-vertical",
            "hex": "f1d9"
        }, {"name": "dots-vertical-circle", "hex": "f7c3"}, {"name": "douban", "hex": "f699"}, {
            "name": "download",
            "hex": "f1da"
        }, {"name": "download-network", "hex": "f6f3"}, {"name": "drag", "hex": "f1db"}, {
            "name": "drag-horizontal",
            "hex": "f1dc"
        }, {"name": "drag-vertical", "hex": "f1dd"}, {"name": "drawing", "hex": "f1de"}, {
            "name": "drawing-box",
            "hex": "f1df"
        }, {"name": "dribbble", "hex": "f1e0"}, {"name": "dribbble-box", "hex": "f1e1"}, {
            "name": "drone",
            "hex": "f1e2"
        }, {"name": "dropbox", "hex": "f1e3"}, {"name": "drupal", "hex": "f1e4"}, {
            "name": "duck",
            "hex": "f1e5"
        }, {"name": "dumbbell", "hex": "f1e6"}, {"name": "ear-hearing", "hex": "f7c4"}, {
            "name": "earth",
            "hex": "f1e7"
        }, {"name": "earth-box", "hex": "f6cc"}, {"name": "earth-box-off", "hex": "f6cd"}, {
            "name": "earth-off",
            "hex": "f1e8"
        }, {"name": "edge", "hex": "f1e9"}, {"name": "eject", "hex": "f1ea"}, {
            "name": "elephant",
            "hex": "f7c5"
        }, {"name": "elevation-decline", "hex": "f1eb"}, {"name": "elevation-rise", "hex": "f1ec"}, {
            "name": "elevator",
            "hex": "f1ed"
        }, {"name": "email", "hex": "f1ee"}, {"name": "email-alert", "hex": "f6ce"}, {
            "name": "email-open",
            "hex": "f1ef"
        }, {"name": "email-open-outline", "hex": "f5ef"}, {
            "name": "email-outline",
            "hex": "f1f0"
        }, {"name": "email-secure", "hex": "f1f1"}, {"name": "email-variant", "hex": "f5f0"}, {
            "name": "emby",
            "hex": "f6b3"
        }, {"name": "emoticon", "hex": "f1f2"}, {"name": "emoticon-cool", "hex": "f1f3"}, {
            "name": "emoticon-dead",
            "hex": "f69a"
        }, {"name": "emoticon-devil", "hex": "f1f4"}, {
            "name": "emoticon-excited",
            "hex": "f69b"
        }, {"name": "emoticon-happy", "hex": "f1f5"}, {
            "name": "emoticon-neutral",
            "hex": "f1f6"
        }, {"name": "emoticon-poop", "hex": "f1f7"}, {
            "name": "emoticon-sad",
            "hex": "f1f8"
        }, {"name": "emoticon-tongue", "hex": "f1f9"}, {"name": "engine", "hex": "f1fa"}, {
            "name": "engine-outline",
            "hex": "f1fb"
        }, {"name": "equal", "hex": "f1fc"}, {"name": "equal-box", "hex": "f1fd"}, {
            "name": "eraser",
            "hex": "f1fe"
        }, {"name": "eraser-variant", "hex": "f642"}, {"name": "escalator", "hex": "f1ff"}, {
            "name": "ethereum",
            "hex": "f869"
        }, {"name": "ethernet", "hex": "f200"}, {
            "name": "ethernet-cable",
            "hex": "f201"
        }, {"name": "ethernet-cable-off", "hex": "f202"}, {"name": "etsy", "hex": "f203"}, {
            "name": "ev-station",
            "hex": "f5f1"
        }, {"name": "eventbrite", "hex": "f7c6"}, {"name": "evernote", "hex": "f204"}, {
            "name": "exclamation",
            "hex": "f205"
        }, {"name": "exit-to-app", "hex": "f206"}, {"name": "export", "hex": "f207"}, {
            "name": "eye",
            "hex": "f208"
        }, {"name": "eye-off", "hex": "f209"}, {"name": "eye-off-outline", "hex": "f6d0"}, {
            "name": "eye-outline",
            "hex": "f6cf"
        }, {"name": "eye-plus", "hex": "f86a"}, {"name": "eye-plus-outline", "hex": "f86b"}, {
            "name": "eye-settings",
            "hex": "f86c"
        }, {"name": "eye-settings-outline", "hex": "f86d"}, {
            "name": "eyedropper",
            "hex": "f20a"
        }, {"name": "eyedropper-variant", "hex": "f20b"}, {"name": "face", "hex": "f643"}, {
            "name": "face-profile",
            "hex": "f644"
        }, {"name": "facebook", "hex": "f20c"}, {"name": "facebook-box", "hex": "f20d"}, {
            "name": "facebook-messenger",
            "hex": "f20e"
        }, {"name": "factory", "hex": "f20f"}, {"name": "fan", "hex": "f210"}, {
            "name": "fan-off",
            "hex": "f81c"
        }, {"name": "fast-forward", "hex": "f211"}, {"name": "fast-forward-outline", "hex": "f6d1"}, {
            "name": "fax",
            "hex": "f212"
        }, {"name": "feather", "hex": "f6d2"}, {"name": "ferry", "hex": "f213"}, {
            "name": "file",
            "hex": "f214"
        }, {"name": "file-account", "hex": "f73a"}, {"name": "file-chart", "hex": "f215"}, {
            "name": "file-check",
            "hex": "f216"
        }, {"name": "file-cloud", "hex": "f217"}, {"name": "file-delimited", "hex": "f218"}, {
            "name": "file-document",
            "hex": "f219"
        }, {"name": "file-document-box", "hex": "f21a"}, {
            "name": "file-excel",
            "hex": "f21b"
        }, {"name": "file-excel-box", "hex": "f21c"}, {"name": "file-export", "hex": "f21d"}, {
            "name": "file-find",
            "hex": "f21e"
        }, {"name": "file-hidden", "hex": "f613"}, {"name": "file-image", "hex": "f21f"}, {
            "name": "file-import",
            "hex": "f220"
        }, {"name": "file-lock", "hex": "f221"}, {"name": "file-multiple", "hex": "f222"}, {
            "name": "file-music",
            "hex": "f223"
        }, {"name": "file-outline", "hex": "f224"}, {"name": "file-pdf", "hex": "f225"}, {
            "name": "file-pdf-box",
            "hex": "f226"
        }, {"name": "file-percent", "hex": "f81d"}, {"name": "file-plus", "hex": "f751"}, {
            "name": "file-powerpoint",
            "hex": "f227"
        }, {"name": "file-powerpoint-box", "hex": "f228"}, {
            "name": "file-presentation-box",
            "hex": "f229"
        }, {"name": "file-question", "hex": "f86e"}, {"name": "file-restore", "hex": "f670"}, {
            "name": "file-send",
            "hex": "f22a"
        }, {"name": "file-tree", "hex": "f645"}, {"name": "file-video", "hex": "f22b"}, {
            "name": "file-word",
            "hex": "f22c"
        }, {"name": "file-word-box", "hex": "f22d"}, {"name": "file-xml", "hex": "f22e"}, {
            "name": "film",
            "hex": "f22f"
        }, {"name": "filmstrip", "hex": "f230"}, {"name": "filmstrip-off", "hex": "f231"}, {
            "name": "filter",
            "hex": "f232"
        }, {"name": "filter-outline", "hex": "f233"}, {
            "name": "filter-remove",
            "hex": "f234"
        }, {"name": "filter-remove-outline", "hex": "f235"}, {
            "name": "filter-variant",
            "hex": "f236"
        }, {"name": "finance", "hex": "f81e"}, {"name": "find-replace", "hex": "f6d3"}, {
            "name": "fingerprint",
            "hex": "f237"
        }, {"name": "fire", "hex": "f238"}, {"name": "firefox", "hex": "f239"}, {
            "name": "fish",
            "hex": "f23a"
        }, {"name": "flag", "hex": "f23b"}, {"name": "flag-checkered", "hex": "f23c"}, {
            "name": "flag-outline",
            "hex": "f23d"
        }, {"name": "flag-triangle", "hex": "f23f"}, {
            "name": "flag-variant",
            "hex": "f240"
        }, {"name": "flag-variant-outline", "hex": "f23e"}, {"name": "flash", "hex": "f241"}, {
            "name": "flash-auto",
            "hex": "f242"
        }, {"name": "flash-circle", "hex": "f81f"}, {"name": "flash-off", "hex": "f243"}, {
            "name": "flash-outline",
            "hex": "f6d4"
        }, {"name": "flash-red-eye", "hex": "f67a"}, {"name": "flashlight", "hex": "f244"}, {
            "name": "flashlight-off",
            "hex": "f245"
        }, {"name": "flask", "hex": "f093"}, {"name": "flask-empty", "hex": "f094"}, {
            "name": "flask-empty-outline",
            "hex": "f095"
        }, {"name": "flask-outline", "hex": "f096"}, {"name": "flattr", "hex": "f246"}, {
            "name": "flip-to-back",
            "hex": "f247"
        }, {"name": "flip-to-front", "hex": "f248"}, {"name": "floor-plan", "hex": "f820"}, {
            "name": "floppy",
            "hex": "f249"
        }, {"name": "flower", "hex": "f24a"}, {"name": "folder", "hex": "f24b"}, {
            "name": "folder-account",
            "hex": "f24c"
        }, {"name": "folder-download", "hex": "f24d"}, {
            "name": "folder-google-drive",
            "hex": "f24e"
        }, {"name": "folder-image", "hex": "f24f"}, {"name": "folder-lock", "hex": "f250"}, {
            "name": "folder-lock-open",
            "hex": "f251"
        }, {"name": "folder-move", "hex": "f252"}, {
            "name": "folder-multiple",
            "hex": "f253"
        }, {"name": "folder-multiple-image", "hex": "f254"}, {
            "name": "folder-multiple-outline",
            "hex": "f255"
        }, {"name": "folder-network", "hex": "f86f"}, {"name": "folder-open", "hex": "f76f"}, {
            "name": "folder-outline",
            "hex": "f256"
        }, {"name": "folder-plus", "hex": "f257"}, {"name": "folder-remove", "hex": "f258"}, {
            "name": "folder-star",
            "hex": "f69c"
        }, {"name": "folder-upload", "hex": "f259"}, {"name": "font-awesome", "hex": "f03a"}, {
            "name": "food",
            "hex": "f25a"
        }, {"name": "food-apple", "hex": "f25b"}, {"name": "food-croissant", "hex": "f7c7"}, {
            "name": "food-fork-drink",
            "hex": "f5f2"
        }, {"name": "food-off", "hex": "f5f3"}, {"name": "food-variant", "hex": "f25c"}, {
            "name": "football",
            "hex": "f25d"
        }, {"name": "football-australian", "hex": "f25e"}, {
            "name": "football-helmet",
            "hex": "f25f"
        }, {"name": "forklift", "hex": "f7c8"}, {
            "name": "format-align-bottom",
            "hex": "f752"
        }, {"name": "format-align-center", "hex": "f260"}, {
            "name": "format-align-justify",
            "hex": "f261"
        }, {"name": "format-align-left", "hex": "f262"}, {
            "name": "format-align-middle",
            "hex": "f753"
        }, {"name": "format-align-right", "hex": "f263"}, {
            "name": "format-align-top",
            "hex": "f754"
        }, {"name": "format-annotation-plus", "hex": "f646"}, {
            "name": "format-bold",
            "hex": "f264"
        }, {"name": "format-clear", "hex": "f265"}, {
            "name": "format-color-fill",
            "hex": "f266"
        }, {"name": "format-color-text", "hex": "f69d"}, {
            "name": "format-float-center",
            "hex": "f267"
        }, {"name": "format-float-left", "hex": "f268"}, {
            "name": "format-float-none",
            "hex": "f269"
        }, {"name": "format-float-right", "hex": "f26a"}, {
            "name": "format-font",
            "hex": "f6d5"
        }, {"name": "format-header-1", "hex": "f26b"}, {
            "name": "format-header-2",
            "hex": "f26c"
        }, {"name": "format-header-3", "hex": "f26d"}, {
            "name": "format-header-4",
            "hex": "f26e"
        }, {"name": "format-header-5", "hex": "f26f"}, {
            "name": "format-header-6",
            "hex": "f270"
        }, {"name": "format-header-decrease", "hex": "f271"}, {
            "name": "format-header-equal",
            "hex": "f272"
        }, {"name": "format-header-increase", "hex": "f273"}, {
            "name": "format-header-pound",
            "hex": "f274"
        }, {"name": "format-horizontal-align-center", "hex": "f61e"}, {
            "name": "format-horizontal-align-left",
            "hex": "f61f"
        }, {"name": "format-horizontal-align-right", "hex": "f620"}, {
            "name": "format-indent-decrease",
            "hex": "f275"
        }, {"name": "format-indent-increase", "hex": "f276"}, {
            "name": "format-italic",
            "hex": "f277"
        }, {"name": "format-line-spacing", "hex": "f278"}, {
            "name": "format-line-style",
            "hex": "f5c8"
        }, {"name": "format-line-weight", "hex": "f5c9"}, {
            "name": "format-list-bulleted",
            "hex": "f279"
        }, {"name": "format-list-bulleted-type", "hex": "f27a"}, {
            "name": "format-list-checks",
            "hex": "f755"
        }, {"name": "format-list-numbers", "hex": "f27b"}, {
            "name": "format-page-break",
            "hex": "f6d6"
        }, {"name": "format-paint", "hex": "f27c"}, {
            "name": "format-paragraph",
            "hex": "f27d"
        }, {"name": "format-pilcrow", "hex": "f6d7"}, {
            "name": "format-quote-close",
            "hex": "f27e"
        }, {"name": "format-quote-open", "hex": "f756"}, {
            "name": "format-rotate-90",
            "hex": "f6a9"
        }, {"name": "format-section", "hex": "f69e"}, {
            "name": "format-size",
            "hex": "f27f"
        }, {"name": "format-strikethrough", "hex": "f280"}, {
            "name": "format-strikethrough-variant",
            "hex": "f281"
        }, {"name": "format-subscript", "hex": "f282"}, {
            "name": "format-superscript",
            "hex": "f283"
        }, {"name": "format-text", "hex": "f284"}, {
            "name": "format-textdirection-l-to-r",
            "hex": "f285"
        }, {"name": "format-textdirection-r-to-l", "hex": "f286"}, {
            "name": "format-title",
            "hex": "f5f4"
        }, {"name": "format-underline", "hex": "f287"}, {
            "name": "format-vertical-align-bottom",
            "hex": "f621"
        }, {"name": "format-vertical-align-center", "hex": "f622"}, {
            "name": "format-vertical-align-top",
            "hex": "f623"
        }, {"name": "format-wrap-inline", "hex": "f288"}, {
            "name": "format-wrap-square",
            "hex": "f289"
        }, {"name": "format-wrap-tight", "hex": "f28a"}, {
            "name": "format-wrap-top-bottom",
            "hex": "f28b"
        }, {"name": "forum", "hex": "f28c"}, {"name": "forum-outline", "hex": "f821"}, {
            "name": "forward",
            "hex": "f28d"
        }, {"name": "foursquare", "hex": "f28e"}, {"name": "fridge", "hex": "f28f"}, {
            "name": "fridge-filled",
            "hex": "f290"
        }, {"name": "fridge-filled-bottom", "hex": "f291"}, {
            "name": "fridge-filled-top",
            "hex": "f292"
        }, {"name": "fuel", "hex": "f7c9"}, {"name": "fullscreen", "hex": "f293"}, {
            "name": "fullscreen-exit",
            "hex": "f294"
        }, {"name": "function", "hex": "f295"}, {"name": "function-variant", "hex": "f870"}, {
            "name": "gamepad",
            "hex": "f296"
        }, {"name": "gamepad-variant", "hex": "f297"}, {"name": "garage", "hex": "f6d8"}, {
            "name": "garage-alert",
            "hex": "f871"
        }, {"name": "garage-open", "hex": "f6d9"}, {"name": "gas-cylinder", "hex": "f647"}, {
            "name": "gas-station",
            "hex": "f298"
        }, {"name": "gate", "hex": "f299"}, {"name": "gauge", "hex": "f29a"}, {
            "name": "gauge-empty",
            "hex": "f872"
        }, {"name": "gauge-full", "hex": "f873"}, {"name": "gauge-low", "hex": "f874"}, {
            "name": "gavel",
            "hex": "f29b"
        }, {"name": "gender-female", "hex": "f29c"}, {
            "name": "gender-male",
            "hex": "f29d"
        }, {"name": "gender-male-female", "hex": "f29e"}, {
            "name": "gender-transgender",
            "hex": "f29f"
        }, {"name": "gesture", "hex": "f7ca"}, {
            "name": "gesture-double-tap",
            "hex": "f73b"
        }, {"name": "gesture-swipe-down", "hex": "f73c"}, {
            "name": "gesture-swipe-left",
            "hex": "f73d"
        }, {"name": "gesture-swipe-right", "hex": "f73e"}, {
            "name": "gesture-swipe-up",
            "hex": "f73f"
        }, {"name": "gesture-tap", "hex": "f740"}, {
            "name": "gesture-two-double-tap",
            "hex": "f741"
        }, {"name": "gesture-two-tap", "hex": "f742"}, {"name": "ghost", "hex": "f2a0"}, {
            "name": "gift",
            "hex": "f2a1"
        }, {"name": "git", "hex": "f2a2"}, {"name": "github-box", "hex": "f2a3"}, {
            "name": "github-circle",
            "hex": "f2a4"
        }, {"name": "github-face", "hex": "f6da"}, {"name": "glass-cocktail", "hex": "f356"}, {
            "name": "glass-flute",
            "hex": "f2a5"
        }, {"name": "glass-mug", "hex": "f2a6"}, {"name": "glass-stange", "hex": "f2a7"}, {
            "name": "glass-tulip",
            "hex": "f2a8"
        }, {"name": "glass-wine", "hex": "f875"}, {"name": "glassdoor", "hex": "f2a9"}, {
            "name": "glasses",
            "hex": "f2aa"
        }, {"name": "gmail", "hex": "f2ab"}, {"name": "gnome", "hex": "f2ac"}, {
            "name": "golf",
            "hex": "f822"
        }, {"name": "gondola", "hex": "f685"}, {"name": "google", "hex": "f2ad"}, {
            "name": "google-analytics",
            "hex": "f7cb"
        }, {"name": "google-assistant", "hex": "f7cc"}, {
            "name": "google-cardboard",
            "hex": "f2ae"
        }, {"name": "google-chrome", "hex": "f2af"}, {
            "name": "google-circles",
            "hex": "f2b0"
        }, {"name": "google-circles-communities", "hex": "f2b1"}, {
            "name": "google-circles-extended",
            "hex": "f2b2"
        }, {"name": "google-circles-group", "hex": "f2b3"}, {
            "name": "google-controller",
            "hex": "f2b4"
        }, {"name": "google-controller-off", "hex": "f2b5"}, {
            "name": "google-drive",
            "hex": "f2b6"
        }, {"name": "google-earth", "hex": "f2b7"}, {"name": "google-glass", "hex": "f2b8"}, {
            "name": "google-home",
            "hex": "f823"
        }, {"name": "google-keep", "hex": "f6db"}, {"name": "google-maps", "hex": "f5f5"}, {
            "name": "google-nearby",
            "hex": "f2b9"
        }, {"name": "google-pages", "hex": "f2ba"}, {
            "name": "google-photos",
            "hex": "f6dc"
        }, {"name": "google-physical-web", "hex": "f2bb"}, {
            "name": "google-play",
            "hex": "f2bc"
        }, {"name": "google-plus", "hex": "f2bd"}, {
            "name": "google-plus-box",
            "hex": "f2be"
        }, {"name": "google-translate", "hex": "f2bf"}, {"name": "google-wallet", "hex": "f2c0"}, {
            "name": "gradient",
            "hex": "f69f"
        }, {"name": "graphql", "hex": "f876"}, {"name": "grease-pencil", "hex": "f648"}, {
            "name": "grid",
            "hex": "f2c1"
        }, {"name": "grid-large", "hex": "f757"}, {"name": "grid-off", "hex": "f2c2"}, {
            "name": "group",
            "hex": "f2c3"
        }, {"name": "guitar-acoustic", "hex": "f770"}, {
            "name": "guitar-electric",
            "hex": "f2c4"
        }, {"name": "guitar-pick", "hex": "f2c5"}, {
            "name": "guitar-pick-outline",
            "hex": "f2c6"
        }, {"name": "guy-fawkes-mask", "hex": "f824"}, {"name": "hackernews", "hex": "f624"}, {
            "name": "hamburger",
            "hex": "f684"
        }, {"name": "hand-pointing-right", "hex": "f2c7"}, {"name": "hanger", "hex": "f2c8"}, {
            "name": "hangouts",
            "hex": "f2c9"
        }, {"name": "harddisk", "hex": "f2ca"}, {"name": "headphones", "hex": "f2cb"}, {
            "name": "headphones-box",
            "hex": "f2cc"
        }, {"name": "headphones-off", "hex": "f7cd"}, {
            "name": "headphones-settings",
            "hex": "f2cd"
        }, {"name": "headset", "hex": "f2ce"}, {"name": "headset-dock", "hex": "f2cf"}, {
            "name": "headset-off",
            "hex": "f2d0"
        }, {"name": "heart", "hex": "f2d1"}, {"name": "heart-box", "hex": "f2d2"}, {
            "name": "heart-box-outline",
            "hex": "f2d3"
        }, {"name": "heart-broken", "hex": "f2d4"}, {"name": "heart-half", "hex": "f6de"}, {
            "name": "heart-half-full",
            "hex": "f6dd"
        }, {"name": "heart-half-outline", "hex": "f6df"}, {
            "name": "heart-off",
            "hex": "f758"
        }, {"name": "heart-outline", "hex": "f2d5"}, {"name": "heart-pulse", "hex": "f5f6"}, {
            "name": "help",
            "hex": "f2d6"
        }, {"name": "help-box", "hex": "f78a"}, {"name": "help-circle", "hex": "f2d7"}, {
            "name": "help-circle-outline",
            "hex": "f625"
        }, {"name": "help-network", "hex": "f6f4"}, {"name": "hexagon", "hex": "f2d8"}, {
            "name": "hexagon-multiple",
            "hex": "f6e0"
        }, {"name": "hexagon-outline", "hex": "f2d9"}, {
            "name": "high-definition",
            "hex": "f7ce"
        }, {"name": "high-definition-box", "hex": "f877"}, {"name": "highway", "hex": "f5f7"}, {
            "name": "history",
            "hex": "f2da"
        }, {"name": "hockey-puck", "hex": "f878"}, {"name": "hockey-sticks", "hex": "f879"}, {
            "name": "hololens",
            "hex": "f2db"
        }, {"name": "home", "hex": "f2dc"}, {"name": "home-account", "hex": "f825"}, {
            "name": "home-alert",
            "hex": "f87a"
        }, {"name": "home-assistant", "hex": "f7cf"}, {
            "name": "home-automation",
            "hex": "f7d0"
        }, {"name": "home-circle", "hex": "f7d1"}, {"name": "home-heart", "hex": "f826"}, {
            "name": "home-map-marker",
            "hex": "f5f8"
        }, {"name": "home-modern", "hex": "f2dd"}, {"name": "home-outline", "hex": "f6a0"}, {
            "name": "home-variant",
            "hex": "f2de"
        }, {"name": "hook", "hex": "f6e1"}, {"name": "hook-off", "hex": "f6e2"}, {
            "name": "hops",
            "hex": "f2df"
        }, {"name": "hospital", "hex": "f2e0"}, {
            "name": "hospital-building",
            "hex": "f2e1"
        }, {"name": "hospital-marker", "hex": "f2e2"}, {"name": "hot-tub", "hex": "f827"}, {
            "name": "hotel",
            "hex": "f2e3"
        }, {"name": "houzz", "hex": "f2e4"}, {"name": "houzz-box", "hex": "f2e5"}, {
            "name": "hulu",
            "hex": "f828"
        }, {"name": "human", "hex": "f2e6"}, {"name": "human-child", "hex": "f2e7"}, {
            "name": "human-female",
            "hex": "f649"
        }, {"name": "human-greeting", "hex": "f64a"}, {
            "name": "human-handsdown",
            "hex": "f64b"
        }, {"name": "human-handsup", "hex": "f64c"}, {
            "name": "human-male",
            "hex": "f64d"
        }, {"name": "human-male-female", "hex": "f2e8"}, {
            "name": "human-pregnant",
            "hex": "f5cf"
        }, {"name": "humble-bundle", "hex": "f743"}, {"name": "ice-cream", "hex": "f829"}, {
            "name": "image",
            "hex": "f2e9"
        }, {"name": "image-album", "hex": "f2ea"}, {"name": "image-area", "hex": "f2eb"}, {
            "name": "image-area-close",
            "hex": "f2ec"
        }, {"name": "image-broken", "hex": "f2ed"}, {
            "name": "image-broken-variant",
            "hex": "f2ee"
        }, {"name": "image-filter", "hex": "f2ef"}, {
            "name": "image-filter-black-white",
            "hex": "f2f0"
        }, {"name": "image-filter-center-focus", "hex": "f2f1"}, {
            "name": "image-filter-center-focus-weak",
            "hex": "f2f2"
        }, {"name": "image-filter-drama", "hex": "f2f3"}, {
            "name": "image-filter-frames",
            "hex": "f2f4"
        }, {"name": "image-filter-hdr", "hex": "f2f5"}, {
            "name": "image-filter-none",
            "hex": "f2f6"
        }, {"name": "image-filter-tilt-shift", "hex": "f2f7"}, {
            "name": "image-filter-vintage",
            "hex": "f2f8"
        }, {"name": "image-multiple", "hex": "f2f9"}, {"name": "image-off", "hex": "f82a"}, {
            "name": "image-plus",
            "hex": "f87b"
        }, {"name": "import", "hex": "f2fa"}, {"name": "inbox", "hex": "f686"}, {
            "name": "inbox-arrow-down",
            "hex": "f2fb"
        }, {"name": "inbox-arrow-up", "hex": "f3d1"}, {"name": "incognito", "hex": "f5f9"}, {
            "name": "infinity",
            "hex": "f6e3"
        }, {"name": "information", "hex": "f2fc"}, {
            "name": "information-outline",
            "hex": "f2fd"
        }, {"name": "information-variant", "hex": "f64e"}, {"name": "instagram", "hex": "f2fe"}, {
            "name": "instapaper",
            "hex": "f2ff"
        }, {"name": "internet-explorer", "hex": "f300"}, {"name": "invert-colors", "hex": "f301"}, {
            "name": "itunes",
            "hex": "f676"
        }, {"name": "jeepney", "hex": "f302"}, {"name": "jira", "hex": "f303"}, {
            "name": "jquery",
            "hex": "f87c"
        }, {"name": "jsfiddle", "hex": "f304"}, {"name": "json", "hex": "f626"}, {
            "name": "karate",
            "hex": "f82b"
        }, {"name": "keg", "hex": "f305"}, {"name": "kettle", "hex": "f5fa"}, {
            "name": "key",
            "hex": "f306"
        }, {"name": "key-change", "hex": "f307"}, {"name": "key-minus", "hex": "f308"}, {
            "name": "key-plus",
            "hex": "f309"
        }, {"name": "key-remove", "hex": "f30a"}, {"name": "key-variant", "hex": "f30b"}, {
            "name": "keyboard",
            "hex": "f30c"
        }, {"name": "keyboard-backspace", "hex": "f30d"}, {
            "name": "keyboard-caps",
            "hex": "f30e"
        }, {"name": "keyboard-close", "hex": "f30f"}, {
            "name": "keyboard-off",
            "hex": "f310"
        }, {"name": "keyboard-return", "hex": "f311"}, {
            "name": "keyboard-tab",
            "hex": "f312"
        }, {"name": "keyboard-variant", "hex": "f313"}, {"name": "kickstarter", "hex": "f744"}, {
            "name": "kodi",
            "hex": "f314"
        }, {"name": "label", "hex": "f315"}, {"name": "label-outline", "hex": "f316"}, {
            "name": "ladybug",
            "hex": "f82c"
        }, {"name": "lambda", "hex": "f627"}, {"name": "lamp", "hex": "f6b4"}, {
            "name": "lan",
            "hex": "f317"
        }, {"name": "lan-connect", "hex": "f318"}, {"name": "lan-disconnect", "hex": "f319"}, {
            "name": "lan-pending",
            "hex": "f31a"
        }, {"name": "language-c", "hex": "f671"}, {"name": "language-cpp", "hex": "f672"}, {
            "name": "language-csharp",
            "hex": "f31b"
        }, {"name": "language-css3", "hex": "f31c"}, {"name": "language-go", "hex": "f7d2"}, {
            "name": "language-html5",
            "hex": "f31d"
        }, {"name": "language-javascript", "hex": "f31e"}, {
            "name": "language-php",
            "hex": "f31f"
        }, {"name": "language-python", "hex": "f320"}, {
            "name": "language-python-text",
            "hex": "f321"
        }, {"name": "language-r", "hex": "f7d3"}, {
            "name": "language-swift",
            "hex": "f6e4"
        }, {"name": "language-typescript", "hex": "f6e5"}, {
            "name": "laptop",
            "hex": "f322"
        }, {"name": "laptop-chromebook", "hex": "f323"}, {"name": "laptop-mac", "hex": "f324"}, {
            "name": "laptop-off",
            "hex": "f6e6"
        }, {"name": "laptop-windows", "hex": "f325"}, {"name": "lastfm", "hex": "f326"}, {
            "name": "lastpass",
            "hex": "f446"
        }, {"name": "launch", "hex": "f327"}, {"name": "lava-lamp", "hex": "f7d4"}, {
            "name": "layers",
            "hex": "f328"
        }, {"name": "layers-off", "hex": "f329"}, {"name": "lead-pencil", "hex": "f64f"}, {
            "name": "leaf",
            "hex": "f32a"
        }, {"name": "led-off", "hex": "f32b"}, {"name": "led-on", "hex": "f32c"}, {
            "name": "led-outline",
            "hex": "f32d"
        }, {"name": "led-strip", "hex": "f7d5"}, {"name": "led-variant-off", "hex": "f32e"}, {
            "name": "led-variant-on",
            "hex": "f32f"
        }, {"name": "led-variant-outline", "hex": "f330"}, {"name": "library", "hex": "f331"}, {
            "name": "library-books",
            "hex": "f332"
        }, {"name": "library-music", "hex": "f333"}, {"name": "library-plus", "hex": "f334"}, {
            "name": "lifebuoy",
            "hex": "f87d"
        }, {"name": "lightbulb", "hex": "f335"}, {
            "name": "lightbulb-on",
            "hex": "f6e7"
        }, {"name": "lightbulb-on-outline", "hex": "f6e8"}, {
            "name": "lightbulb-outline",
            "hex": "f336"
        }, {"name": "link", "hex": "f337"}, {"name": "link-off", "hex": "f338"}, {
            "name": "link-variant",
            "hex": "f339"
        }, {"name": "link-variant-off", "hex": "f33a"}, {"name": "linkedin", "hex": "f33b"}, {
            "name": "linkedin-box",
            "hex": "f33c"
        }, {"name": "linux", "hex": "f33d"}, {"name": "loading", "hex": "f771"}, {
            "name": "lock",
            "hex": "f33e"
        }, {"name": "lock-open", "hex": "f33f"}, {"name": "lock-open-outline", "hex": "f340"}, {
            "name": "lock-outline",
            "hex": "f341"
        }, {"name": "lock-pattern", "hex": "f6e9"}, {"name": "lock-plus", "hex": "f5fb"}, {
            "name": "lock-reset",
            "hex": "f772"
        }, {"name": "locker", "hex": "f7d6"}, {"name": "locker-multiple", "hex": "f7d7"}, {
            "name": "login",
            "hex": "f342"
        }, {"name": "login-variant", "hex": "f5fc"}, {"name": "logout", "hex": "f343"}, {
            "name": "logout-variant",
            "hex": "f5fd"
        }, {"name": "looks", "hex": "f344"}, {"name": "loop", "hex": "f6ea"}, {
            "name": "loupe",
            "hex": "f345"
        }, {"name": "lumx", "hex": "f346"}, {"name": "magnet", "hex": "f347"}, {
            "name": "magnet-on",
            "hex": "f348"
        }, {"name": "magnify", "hex": "f349"}, {
            "name": "magnify-minus",
            "hex": "f34a"
        }, {"name": "magnify-minus-outline", "hex": "f6eb"}, {
            "name": "magnify-plus",
            "hex": "f34b"
        }, {"name": "magnify-plus-outline", "hex": "f6ec"}, {"name": "mail-ru", "hex": "f34c"}, {
            "name": "mailbox",
            "hex": "f6ed"
        }, {"name": "map", "hex": "f34d"}, {"name": "map-marker", "hex": "f34e"}, {
            "name": "map-marker-circle",
            "hex": "f34f"
        }, {"name": "map-marker-minus", "hex": "f650"}, {
            "name": "map-marker-multiple",
            "hex": "f350"
        }, {"name": "map-marker-off", "hex": "f351"}, {
            "name": "map-marker-outline",
            "hex": "f7d8"
        }, {"name": "map-marker-plus", "hex": "f651"}, {"name": "map-marker-radius", "hex": "f352"}, {
            "name": "margin",
            "hex": "f353"
        }, {"name": "markdown", "hex": "f354"}, {"name": "marker", "hex": "f652"}, {
            "name": "marker-check",
            "hex": "f355"
        }, {"name": "material-ui", "hex": "f357"}, {"name": "math-compass", "hex": "f358"}, {
            "name": "matrix",
            "hex": "f628"
        }, {"name": "maxcdn", "hex": "f359"}, {"name": "medical-bag", "hex": "f6ee"}, {
            "name": "medium",
            "hex": "f35a"
        }, {"name": "memory", "hex": "f35b"}, {"name": "menu", "hex": "f35c"}, {
            "name": "menu-down",
            "hex": "f35d"
        }, {"name": "menu-down-outline", "hex": "f6b5"}, {"name": "menu-left", "hex": "f35e"}, {
            "name": "menu-right",
            "hex": "f35f"
        }, {"name": "menu-up", "hex": "f360"}, {"name": "menu-up-outline", "hex": "f6b6"}, {
            "name": "message",
            "hex": "f361"
        }, {"name": "message-alert", "hex": "f362"}, {
            "name": "message-bulleted",
            "hex": "f6a1"
        }, {"name": "message-bulleted-off", "hex": "f6a2"}, {
            "name": "message-draw",
            "hex": "f363"
        }, {"name": "message-image", "hex": "f364"}, {
            "name": "message-outline",
            "hex": "f365"
        }, {"name": "message-plus", "hex": "f653"}, {
            "name": "message-processing",
            "hex": "f366"
        }, {"name": "message-reply", "hex": "f367"}, {
            "name": "message-reply-text",
            "hex": "f368"
        }, {"name": "message-settings", "hex": "f6ef"}, {
            "name": "message-settings-variant",
            "hex": "f6f0"
        }, {"name": "message-text", "hex": "f369"}, {
            "name": "message-text-outline",
            "hex": "f36a"
        }, {"name": "message-video", "hex": "f36b"}, {"name": "meteor", "hex": "f629"}, {
            "name": "metronome",
            "hex": "f7d9"
        }, {"name": "metronome-tick", "hex": "f7da"}, {"name": "micro-sd", "hex": "f7db"}, {
            "name": "microphone",
            "hex": "f36c"
        }, {"name": "microphone-off", "hex": "f36d"}, {
            "name": "microphone-outline",
            "hex": "f36e"
        }, {"name": "microphone-settings", "hex": "f36f"}, {
            "name": "microphone-variant",
            "hex": "f370"
        }, {"name": "microphone-variant-off", "hex": "f371"}, {
            "name": "microscope",
            "hex": "f654"
        }, {"name": "microsoft", "hex": "f372"}, {"name": "minecraft", "hex": "f373"}, {
            "name": "minus",
            "hex": "f374"
        }, {"name": "minus-box", "hex": "f375"}, {"name": "minus-box-outline", "hex": "f6f1"}, {
            "name": "minus-circle",
            "hex": "f376"
        }, {"name": "minus-circle-outline", "hex": "f377"}, {
            "name": "minus-network",
            "hex": "f378"
        }, {"name": "mixcloud", "hex": "f62a"}, {"name": "mixed-reality", "hex": "f87e"}, {
            "name": "mixer",
            "hex": "f7dc"
        }, {"name": "monitor", "hex": "f379"}, {"name": "monitor-multiple", "hex": "f37a"}, {
            "name": "more",
            "hex": "f37b"
        }, {"name": "motorbike", "hex": "f37c"}, {"name": "mouse", "hex": "f37d"}, {
            "name": "mouse-off",
            "hex": "f37e"
        }, {"name": "mouse-variant", "hex": "f37f"}, {
            "name": "mouse-variant-off",
            "hex": "f380"
        }, {"name": "move-resize", "hex": "f655"}, {"name": "move-resize-variant", "hex": "f656"}, {
            "name": "movie",
            "hex": "f381"
        }, {"name": "movie-roll", "hex": "f7dd"}, {
            "name": "multiplication",
            "hex": "f382"
        }, {"name": "multiplication-box", "hex": "f383"}, {
            "name": "mushroom",
            "hex": "f7de"
        }, {"name": "mushroom-outline", "hex": "f7df"}, {"name": "music", "hex": "f759"}, {
            "name": "music-box",
            "hex": "f384"
        }, {"name": "music-box-outline", "hex": "f385"}, {"name": "music-circle", "hex": "f386"}, {
            "name": "music-note",
            "hex": "f387"
        }, {"name": "music-note-bluetooth", "hex": "f5fe"}, {
            "name": "music-note-bluetooth-off",
            "hex": "f5ff"
        }, {"name": "music-note-eighth", "hex": "f388"}, {
            "name": "music-note-half",
            "hex": "f389"
        }, {"name": "music-note-off", "hex": "f38a"}, {
            "name": "music-note-quarter",
            "hex": "f38b"
        }, {"name": "music-note-sixteenth", "hex": "f38c"}, {
            "name": "music-note-whole",
            "hex": "f38d"
        }, {"name": "music-off", "hex": "f75a"}, {"name": "nativescript", "hex": "f87f"}, {
            "name": "nature",
            "hex": "f38e"
        }, {"name": "nature-people", "hex": "f38f"}, {"name": "navigation", "hex": "f390"}, {
            "name": "near-me",
            "hex": "f5cd"
        }, {"name": "needle", "hex": "f391"}, {"name": "nest-protect", "hex": "f392"}, {
            "name": "nest-thermostat",
            "hex": "f393"
        }, {"name": "netflix", "hex": "f745"}, {"name": "network", "hex": "f6f2"}, {
            "name": "new-box",
            "hex": "f394"
        }, {"name": "newspaper", "hex": "f395"}, {"name": "nfc", "hex": "f396"}, {
            "name": "nfc-tap",
            "hex": "f397"
        }, {"name": "nfc-variant", "hex": "f398"}, {"name": "ninja", "hex": "f773"}, {
            "name": "nintendo-switch",
            "hex": "f7e0"
        }, {"name": "nodejs", "hex": "f399"}, {"name": "note", "hex": "f39a"}, {
            "name": "note-multiple",
            "hex": "f6b7"
        }, {"name": "note-multiple-outline", "hex": "f6b8"}, {
            "name": "note-outline",
            "hex": "f39b"
        }, {"name": "note-plus", "hex": "f39c"}, {"name": "note-plus-outline", "hex": "f39d"}, {
            "name": "note-text",
            "hex": "f39e"
        }, {"name": "notebook", "hex": "f82d"}, {"name": "notification-clear-all", "hex": "f39f"}, {
            "name": "npm",
            "hex": "f6f6"
        }, {"name": "nuke", "hex": "f6a3"}, {"name": "null", "hex": "f7e1"}, {
            "name": "numeric",
            "hex": "f3a0"
        }, {"name": "numeric-0-box", "hex": "f3a1"}, {
            "name": "numeric-0-box-multiple-outline",
            "hex": "f3a2"
        }, {"name": "numeric-0-box-outline", "hex": "f3a3"}, {
            "name": "numeric-1-box",
            "hex": "f3a4"
        }, {"name": "numeric-1-box-multiple-outline", "hex": "f3a5"}, {
            "name": "numeric-1-box-outline",
            "hex": "f3a6"
        }, {"name": "numeric-2-box", "hex": "f3a7"}, {
            "name": "numeric-2-box-multiple-outline",
            "hex": "f3a8"
        }, {"name": "numeric-2-box-outline", "hex": "f3a9"}, {
            "name": "numeric-3-box",
            "hex": "f3aa"
        }, {"name": "numeric-3-box-multiple-outline", "hex": "f3ab"}, {
            "name": "numeric-3-box-outline",
            "hex": "f3ac"
        }, {"name": "numeric-4-box", "hex": "f3ad"}, {
            "name": "numeric-4-box-multiple-outline",
            "hex": "f3ae"
        }, {"name": "numeric-4-box-outline", "hex": "f3af"}, {
            "name": "numeric-5-box",
            "hex": "f3b0"
        }, {"name": "numeric-5-box-multiple-outline", "hex": "f3b1"}, {
            "name": "numeric-5-box-outline",
            "hex": "f3b2"
        }, {"name": "numeric-6-box", "hex": "f3b3"}, {
            "name": "numeric-6-box-multiple-outline",
            "hex": "f3b4"
        }, {"name": "numeric-6-box-outline", "hex": "f3b5"}, {
            "name": "numeric-7-box",
            "hex": "f3b6"
        }, {"name": "numeric-7-box-multiple-outline", "hex": "f3b7"}, {
            "name": "numeric-7-box-outline",
            "hex": "f3b8"
        }, {"name": "numeric-8-box", "hex": "f3b9"}, {
            "name": "numeric-8-box-multiple-outline",
            "hex": "f3ba"
        }, {"name": "numeric-8-box-outline", "hex": "f3bb"}, {
            "name": "numeric-9-box",
            "hex": "f3bc"
        }, {"name": "numeric-9-box-multiple-outline", "hex": "f3bd"}, {
            "name": "numeric-9-box-outline",
            "hex": "f3be"
        }, {"name": "numeric-9-plus-box", "hex": "f3bf"}, {
            "name": "numeric-9-plus-box-multiple-outline",
            "hex": "f3c0"
        }, {"name": "numeric-9-plus-box-outline", "hex": "f3c1"}, {"name": "nut", "hex": "f6f7"}, {
            "name": "nutrition",
            "hex": "f3c2"
        }, {"name": "oar", "hex": "f67b"}, {"name": "octagon", "hex": "f3c3"}, {
            "name": "octagon-outline",
            "hex": "f3c4"
        }, {"name": "octagram", "hex": "f6f8"}, {"name": "octagram-outline", "hex": "f774"}, {
            "name": "odnoklassniki",
            "hex": "f3c5"
        }, {"name": "office", "hex": "f3c6"}, {"name": "oil", "hex": "f3c7"}, {
            "name": "oil-temperature",
            "hex": "f3c8"
        }, {"name": "omega", "hex": "f3c9"}, {"name": "onedrive", "hex": "f3ca"}, {
            "name": "onenote",
            "hex": "f746"
        }, {"name": "onepassword", "hex": "f880"}, {"name": "opacity", "hex": "f5cc"}, {
            "name": "open-in-app",
            "hex": "f3cb"
        }, {"name": "open-in-new", "hex": "f3cc"}, {"name": "openid", "hex": "f3cd"}, {
            "name": "opera",
            "hex": "f3ce"
        }, {"name": "orbit", "hex": "f018"}, {"name": "ornament", "hex": "f3cf"}, {
            "name": "ornament-variant",
            "hex": "f3d0"
        }, {"name": "owl", "hex": "f3d2"}, {"name": "package", "hex": "f3d3"}, {
            "name": "package-down",
            "hex": "f3d4"
        }, {"name": "package-up", "hex": "f3d5"}, {
            "name": "package-variant",
            "hex": "f3d6"
        }, {"name": "package-variant-closed", "hex": "f3d7"}, {
            "name": "page-first",
            "hex": "f600"
        }, {"name": "page-last", "hex": "f601"}, {
            "name": "page-layout-body",
            "hex": "f6f9"
        }, {"name": "page-layout-footer", "hex": "f6fa"}, {
            "name": "page-layout-header",
            "hex": "f6fb"
        }, {"name": "page-layout-sidebar-left", "hex": "f6fc"}, {
            "name": "page-layout-sidebar-right",
            "hex": "f6fd"
        }, {"name": "palette", "hex": "f3d8"}, {"name": "palette-advanced", "hex": "f3d9"}, {
            "name": "panda",
            "hex": "f3da"
        }, {"name": "pandora", "hex": "f3db"}, {"name": "panorama", "hex": "f3dc"}, {
            "name": "panorama-fisheye",
            "hex": "f3dd"
        }, {"name": "panorama-horizontal", "hex": "f3de"}, {
            "name": "panorama-vertical",
            "hex": "f3df"
        }, {"name": "panorama-wide-angle", "hex": "f3e0"}, {
            "name": "paper-cut-vertical",
            "hex": "f3e1"
        }, {"name": "paperclip", "hex": "f3e2"}, {"name": "parking", "hex": "f3e3"}, {
            "name": "passport",
            "hex": "f7e2"
        }, {"name": "patreon", "hex": "f881"}, {"name": "pause", "hex": "f3e4"}, {
            "name": "pause-circle",
            "hex": "f3e5"
        }, {"name": "pause-circle-outline", "hex": "f3e6"}, {
            "name": "pause-octagon",
            "hex": "f3e7"
        }, {"name": "pause-octagon-outline", "hex": "f3e8"}, {"name": "paw", "hex": "f3e9"}, {
            "name": "paw-off",
            "hex": "f657"
        }, {"name": "paypal", "hex": "f882"}, {"name": "peace", "hex": "f883"}, {
            "name": "pen",
            "hex": "f3ea"
        }, {"name": "pencil", "hex": "f3eb"}, {"name": "pencil-box", "hex": "f3ec"}, {
            "name": "pencil-box-outline",
            "hex": "f3ed"
        }, {"name": "pencil-circle", "hex": "f6fe"}, {
            "name": "pencil-circle-outline",
            "hex": "f775"
        }, {"name": "pencil-lock", "hex": "f3ee"}, {"name": "pencil-off", "hex": "f3ef"}, {
            "name": "pentagon",
            "hex": "f6ff"
        }, {"name": "pentagon-outline", "hex": "f700"}, {
            "name": "percent",
            "hex": "f3f0"
        }, {"name": "periodic-table-co2", "hex": "f7e3"}, {"name": "periscope", "hex": "f747"}, {
            "name": "pharmacy",
            "hex": "f3f1"
        }, {"name": "phone", "hex": "f3f2"}, {"name": "phone-bluetooth", "hex": "f3f3"}, {
            "name": "phone-classic",
            "hex": "f602"
        }, {"name": "phone-forward", "hex": "f3f4"}, {"name": "phone-hangup", "hex": "f3f5"}, {
            "name": "phone-in-talk",
            "hex": "f3f6"
        }, {"name": "phone-incoming", "hex": "f3f7"}, {"name": "phone-locked", "hex": "f3f8"}, {
            "name": "phone-log",
            "hex": "f3f9"
        }, {"name": "phone-minus", "hex": "f658"}, {"name": "phone-missed", "hex": "f3fa"}, {
            "name": "phone-outgoing",
            "hex": "f3fb"
        }, {"name": "phone-paused", "hex": "f3fc"}, {"name": "phone-plus", "hex": "f659"}, {
            "name": "phone-return",
            "hex": "f82e"
        }, {"name": "phone-rotate-landscape", "hex": "f884"}, {
            "name": "phone-rotate-portrait",
            "hex": "f885"
        }, {"name": "phone-settings", "hex": "f3fd"}, {"name": "phone-voip", "hex": "f3fe"}, {
            "name": "pi",
            "hex": "f3ff"
        }, {"name": "pi-box", "hex": "f400"}, {"name": "piano", "hex": "f67c"}, {
            "name": "pier",
            "hex": "f886"
        }, {"name": "pier-crane", "hex": "f887"}, {"name": "pig", "hex": "f401"}, {
            "name": "pill",
            "hex": "f402"
        }, {"name": "pillar", "hex": "f701"}, {"name": "pin", "hex": "f403"}, {
            "name": "pin-off",
            "hex": "f404"
        }, {"name": "pine-tree", "hex": "f405"}, {"name": "pine-tree-box", "hex": "f406"}, {
            "name": "pinterest",
            "hex": "f407"
        }, {"name": "pinterest-box", "hex": "f408"}, {"name": "pipe", "hex": "f7e4"}, {
            "name": "pipe-disconnected",
            "hex": "f7e5"
        }, {"name": "pipe-leak", "hex": "f888"}, {"name": "pistol", "hex": "f702"}, {
            "name": "piston",
            "hex": "f889"
        }, {"name": "pizza", "hex": "f409"}, {"name": "plane-shield", "hex": "f6ba"}, {
            "name": "play",
            "hex": "f40a"
        }, {"name": "play-box-outline", "hex": "f40b"}, {
            "name": "play-circle",
            "hex": "f40c"
        }, {"name": "play-circle-outline", "hex": "f40d"}, {
            "name": "play-network",
            "hex": "f88a"
        }, {"name": "play-pause", "hex": "f40e"}, {
            "name": "play-protected-content",
            "hex": "f40f"
        }, {"name": "playlist-check", "hex": "f5c7"}, {
            "name": "playlist-minus",
            "hex": "f410"
        }, {"name": "playlist-play", "hex": "f411"}, {
            "name": "playlist-plus",
            "hex": "f412"
        }, {"name": "playlist-remove", "hex": "f413"}, {"name": "playstation", "hex": "f414"}, {
            "name": "plex",
            "hex": "f6b9"
        }, {"name": "plus", "hex": "f415"}, {"name": "plus-box", "hex": "f416"}, {
            "name": "plus-box-outline",
            "hex": "f703"
        }, {"name": "plus-circle", "hex": "f417"}, {
            "name": "plus-circle-multiple-outline",
            "hex": "f418"
        }, {"name": "plus-circle-outline", "hex": "f419"}, {"name": "plus-network", "hex": "f41a"}, {
            "name": "plus-one",
            "hex": "f41b"
        }, {"name": "plus-outline", "hex": "f704"}, {"name": "pocket", "hex": "f41c"}, {
            "name": "pokeball",
            "hex": "f41d"
        }, {"name": "poker-chip", "hex": "f82f"}, {"name": "polaroid", "hex": "f41e"}, {
            "name": "poll",
            "hex": "f41f"
        }, {"name": "poll-box", "hex": "f420"}, {"name": "polymer", "hex": "f421"}, {
            "name": "pool",
            "hex": "f606"
        }, {"name": "popcorn", "hex": "f422"}, {"name": "pot", "hex": "f65a"}, {
            "name": "pot-mix",
            "hex": "f65b"
        }, {"name": "pound", "hex": "f423"}, {"name": "pound-box", "hex": "f424"}, {
            "name": "power",
            "hex": "f425"
        }, {"name": "power-plug", "hex": "f6a4"}, {"name": "power-plug-off", "hex": "f6a5"}, {
            "name": "power-settings",
            "hex": "f426"
        }, {"name": "power-socket", "hex": "f427"}, {
            "name": "power-socket-eu",
            "hex": "f7e6"
        }, {"name": "power-socket-uk", "hex": "f7e7"}, {
            "name": "power-socket-us",
            "hex": "f7e8"
        }, {"name": "prescription", "hex": "f705"}, {
            "name": "presentation",
            "hex": "f428"
        }, {"name": "presentation-play", "hex": "f429"}, {"name": "printer", "hex": "f42a"}, {
            "name": "printer-3d",
            "hex": "f42b"
        }, {"name": "printer-alert", "hex": "f42c"}, {
            "name": "printer-settings",
            "hex": "f706"
        }, {"name": "priority-high", "hex": "f603"}, {
            "name": "priority-low",
            "hex": "f604"
        }, {"name": "professional-hexagon", "hex": "f42d"}, {
            "name": "projector",
            "hex": "f42e"
        }, {"name": "projector-screen", "hex": "f42f"}, {"name": "publish", "hex": "f6a6"}, {
            "name": "pulse",
            "hex": "f430"
        }, {"name": "puzzle", "hex": "f431"}, {"name": "qqchat", "hex": "f605"}, {
            "name": "qrcode",
            "hex": "f432"
        }, {"name": "qrcode-scan", "hex": "f433"}, {"name": "quadcopter", "hex": "f434"}, {
            "name": "quality-high",
            "hex": "f435"
        }, {"name": "quicktime", "hex": "f436"}, {"name": "radar", "hex": "f437"}, {
            "name": "radiator",
            "hex": "f438"
        }, {"name": "radio", "hex": "f439"}, {"name": "radio-handheld", "hex": "f43a"}, {
            "name": "radio-tower",
            "hex": "f43b"
        }, {"name": "radioactive", "hex": "f43c"}, {
            "name": "radiobox-blank",
            "hex": "f43d"
        }, {"name": "radiobox-marked", "hex": "f43e"}, {"name": "raspberrypi", "hex": "f43f"}, {
            "name": "ray-end",
            "hex": "f440"
        }, {"name": "ray-end-arrow", "hex": "f441"}, {"name": "ray-start", "hex": "f442"}, {
            "name": "ray-start-arrow",
            "hex": "f443"
        }, {"name": "ray-start-end", "hex": "f444"}, {"name": "ray-vertex", "hex": "f445"}, {
            "name": "react",
            "hex": "f707"
        }, {"name": "read", "hex": "f447"}, {"name": "receipt", "hex": "f449"}, {
            "name": "record",
            "hex": "f44a"
        }, {"name": "record-rec", "hex": "f44b"}, {"name": "recycle", "hex": "f44c"}, {
            "name": "reddit",
            "hex": "f44d"
        }, {"name": "redo", "hex": "f44e"}, {"name": "redo-variant", "hex": "f44f"}, {
            "name": "refresh",
            "hex": "f450"
        }, {"name": "regex", "hex": "f451"}, {"name": "relative-scale", "hex": "f452"}, {
            "name": "reload",
            "hex": "f453"
        }, {"name": "reminder", "hex": "f88b"}, {"name": "remote", "hex": "f454"}, {
            "name": "rename-box",
            "hex": "f455"
        }, {"name": "reorder-horizontal", "hex": "f687"}, {
            "name": "reorder-vertical",
            "hex": "f688"
        }, {"name": "repeat", "hex": "f456"}, {"name": "repeat-off", "hex": "f457"}, {
            "name": "repeat-once",
            "hex": "f458"
        }, {"name": "replay", "hex": "f459"}, {"name": "reply", "hex": "f45a"}, {
            "name": "reply-all",
            "hex": "f45b"
        }, {"name": "reproduction", "hex": "f45c"}, {
            "name": "resize-bottom-right",
            "hex": "f45d"
        }, {"name": "responsive", "hex": "f45e"}, {"name": "restart", "hex": "f708"}, {
            "name": "restore",
            "hex": "f6a7"
        }, {"name": "rewind", "hex": "f45f"}, {"name": "rewind-outline", "hex": "f709"}, {
            "name": "rhombus",
            "hex": "f70a"
        }, {"name": "rhombus-outline", "hex": "f70b"}, {"name": "ribbon", "hex": "f460"}, {
            "name": "rice",
            "hex": "f7e9"
        }, {"name": "ring", "hex": "f7ea"}, {"name": "road", "hex": "f461"}, {
            "name": "road-variant",
            "hex": "f462"
        }, {"name": "robot", "hex": "f6a8"}, {"name": "rocket", "hex": "f463"}, {
            "name": "room-service",
            "hex": "f88c"
        }, {"name": "roomba", "hex": "f70c"}, {"name": "rotate-3d", "hex": "f464"}, {
            "name": "rotate-left",
            "hex": "f465"
        }, {"name": "rotate-left-variant", "hex": "f466"}, {
            "name": "rotate-right",
            "hex": "f467"
        }, {"name": "rotate-right-variant", "hex": "f468"}, {
            "name": "rounded-corner",
            "hex": "f607"
        }, {"name": "router-wireless", "hex": "f469"}, {"name": "routes", "hex": "f46a"}, {
            "name": "rowing",
            "hex": "f608"
        }, {"name": "rss", "hex": "f46b"}, {"name": "rss-box", "hex": "f46c"}, {
            "name": "ruler",
            "hex": "f46d"
        }, {"name": "run", "hex": "f70d"}, {"name": "run-fast", "hex": "f46e"}, {
            "name": "sale",
            "hex": "f46f"
        }, {"name": "salesforce", "hex": "f88d"}, {"name": "sass", "hex": "f7eb"}, {
            "name": "satellite",
            "hex": "f470"
        }, {"name": "satellite-variant", "hex": "f471"}, {"name": "saxophone", "hex": "f609"}, {
            "name": "scale",
            "hex": "f472"
        }, {"name": "scale-balance", "hex": "f5d1"}, {"name": "scale-bathroom", "hex": "f473"}, {
            "name": "scanner",
            "hex": "f6aa"
        }, {"name": "school", "hex": "f474"}, {
            "name": "screen-rotation",
            "hex": "f475"
        }, {"name": "screen-rotation-lock", "hex": "f476"}, {"name": "screwdriver", "hex": "f477"}, {
            "name": "script",
            "hex": "f478"
        }, {"name": "sd", "hex": "f479"}, {"name": "seal", "hex": "f47a"}, {
            "name": "search-web",
            "hex": "f70e"
        }, {"name": "seat-flat", "hex": "f47b"}, {
            "name": "seat-flat-angled",
            "hex": "f47c"
        }, {"name": "seat-individual-suite", "hex": "f47d"}, {
            "name": "seat-legroom-extra",
            "hex": "f47e"
        }, {"name": "seat-legroom-normal", "hex": "f47f"}, {
            "name": "seat-legroom-reduced",
            "hex": "f480"
        }, {"name": "seat-recline-extra", "hex": "f481"}, {
            "name": "seat-recline-normal",
            "hex": "f482"
        }, {"name": "security", "hex": "f483"}, {"name": "security-account", "hex": "f88e"}, {
            "name": "security-home",
            "hex": "f689"
        }, {"name": "security-network", "hex": "f484"}, {"name": "select", "hex": "f485"}, {
            "name": "select-all",
            "hex": "f486"
        }, {"name": "select-inverse", "hex": "f487"}, {"name": "select-off", "hex": "f488"}, {
            "name": "selection",
            "hex": "f489"
        }, {"name": "selection-off", "hex": "f776"}, {"name": "send", "hex": "f48a"}, {
            "name": "send-secure",
            "hex": "f7ec"
        }, {"name": "serial-port", "hex": "f65c"}, {"name": "server", "hex": "f48b"}, {
            "name": "server-minus",
            "hex": "f48c"
        }, {"name": "server-network", "hex": "f48d"}, {
            "name": "server-network-off",
            "hex": "f48e"
        }, {"name": "server-off", "hex": "f48f"}, {"name": "server-plus", "hex": "f490"}, {
            "name": "server-remove",
            "hex": "f491"
        }, {"name": "server-security", "hex": "f492"}, {"name": "set-all", "hex": "f777"}, {
            "name": "set-center",
            "hex": "f778"
        }, {"name": "set-center-right", "hex": "f779"}, {"name": "set-left", "hex": "f77a"}, {
            "name": "set-left-center",
            "hex": "f77b"
        }, {"name": "set-left-right", "hex": "f77c"}, {"name": "set-none", "hex": "f77d"}, {
            "name": "set-right",
            "hex": "f77e"
        }, {"name": "settings", "hex": "f493"}, {"name": "settings-box", "hex": "f494"}, {
            "name": "shape",
            "hex": "f830"
        }, {"name": "shape-circle-plus", "hex": "f65d"}, {
            "name": "shape-outline",
            "hex": "f831"
        }, {"name": "shape-plus", "hex": "f495"}, {
            "name": "shape-polygon-plus",
            "hex": "f65e"
        }, {"name": "shape-rectangle-plus", "hex": "f65f"}, {
            "name": "shape-square-plus",
            "hex": "f660"
        }, {"name": "share", "hex": "f496"}, {"name": "share-variant", "hex": "f497"}, {
            "name": "shield",
            "hex": "f498"
        }, {"name": "shield-half-full", "hex": "f77f"}, {
            "name": "shield-outline",
            "hex": "f499"
        }, {"name": "ship-wheel", "hex": "f832"}, {"name": "shopping", "hex": "f49a"}, {
            "name": "shopping-music",
            "hex": "f49b"
        }, {"name": "shovel", "hex": "f70f"}, {"name": "shovel-off", "hex": "f710"}, {
            "name": "shredder",
            "hex": "f49c"
        }, {"name": "shuffle", "hex": "f49d"}, {"name": "shuffle-disabled", "hex": "f49e"}, {
            "name": "shuffle-variant",
            "hex": "f49f"
        }, {"name": "sigma", "hex": "f4a0"}, {"name": "sigma-lower", "hex": "f62b"}, {
            "name": "sign-caution",
            "hex": "f4a1"
        }, {"name": "sign-direction", "hex": "f780"}, {"name": "sign-text", "hex": "f781"}, {
            "name": "signal",
            "hex": "f4a2"
        }, {"name": "signal-2g", "hex": "f711"}, {"name": "signal-3g", "hex": "f712"}, {
            "name": "signal-4g",
            "hex": "f713"
        }, {"name": "signal-hspa", "hex": "f714"}, {"name": "signal-hspa-plus", "hex": "f715"}, {
            "name": "signal-off",
            "hex": "f782"
        }, {"name": "signal-variant", "hex": "f60a"}, {"name": "silverware", "hex": "f4a3"}, {
            "name": "silverware-fork",
            "hex": "f4a4"
        }, {"name": "silverware-spoon", "hex": "f4a5"}, {"name": "silverware-variant", "hex": "f4a6"}, {
            "name": "sim",
            "hex": "f4a7"
        }, {"name": "sim-alert", "hex": "f4a8"}, {"name": "sim-off", "hex": "f4a9"}, {
            "name": "sitemap",
            "hex": "f4aa"
        }, {"name": "skip-backward", "hex": "f4ab"}, {"name": "skip-forward", "hex": "f4ac"}, {
            "name": "skip-next",
            "hex": "f4ad"
        }, {"name": "skip-next-circle", "hex": "f661"}, {
            "name": "skip-next-circle-outline",
            "hex": "f662"
        }, {"name": "skip-previous", "hex": "f4ae"}, {
            "name": "skip-previous-circle",
            "hex": "f663"
        }, {"name": "skip-previous-circle-outline", "hex": "f664"}, {"name": "skull", "hex": "f68b"}, {
            "name": "skype",
            "hex": "f4af"
        }, {"name": "skype-business", "hex": "f4b0"}, {"name": "slack", "hex": "f4b1"}, {
            "name": "sleep",
            "hex": "f4b2"
        }, {"name": "sleep-off", "hex": "f4b3"}, {"name": "smoking", "hex": "f4b4"}, {
            "name": "smoking-off",
            "hex": "f4b5"
        }, {"name": "snapchat", "hex": "f4b6"}, {"name": "snowflake", "hex": "f716"}, {
            "name": "snowman",
            "hex": "f4b7"
        }, {"name": "soccer", "hex": "f4b8"}, {"name": "soccer-field", "hex": "f833"}, {
            "name": "sofa",
            "hex": "f4b9"
        }, {"name": "solid", "hex": "f68c"}, {"name": "sort", "hex": "f4ba"}, {
            "name": "sort-alphabetical",
            "hex": "f4bb"
        }, {"name": "sort-ascending", "hex": "f4bc"}, {
            "name": "sort-descending",
            "hex": "f4bd"
        }, {"name": "sort-numeric", "hex": "f4be"}, {"name": "sort-variant", "hex": "f4bf"}, {
            "name": "soundcloud",
            "hex": "f4c0"
        }, {"name": "source-branch", "hex": "f62c"}, {
            "name": "source-commit",
            "hex": "f717"
        }, {"name": "source-commit-end", "hex": "f718"}, {
            "name": "source-commit-end-local",
            "hex": "f719"
        }, {"name": "source-commit-local", "hex": "f71a"}, {
            "name": "source-commit-next-local",
            "hex": "f71b"
        }, {"name": "source-commit-start", "hex": "f71c"}, {
            "name": "source-commit-start-next-local",
            "hex": "f71d"
        }, {"name": "source-fork", "hex": "f4c1"}, {"name": "source-merge", "hex": "f62d"}, {
            "name": "source-pull",
            "hex": "f4c2"
        }, {"name": "soy-sauce", "hex": "f7ed"}, {"name": "speaker", "hex": "f4c3"}, {
            "name": "speaker-off",
            "hex": "f4c4"
        }, {"name": "speaker-wireless", "hex": "f71e"}, {"name": "speedometer", "hex": "f4c5"}, {
            "name": "spellcheck",
            "hex": "f4c6"
        }, {"name": "spotify", "hex": "f4c7"}, {"name": "spotlight", "hex": "f4c8"}, {
            "name": "spotlight-beam",
            "hex": "f4c9"
        }, {"name": "spray", "hex": "f665"}, {"name": "square", "hex": "f763"}, {
            "name": "square-inc",
            "hex": "f4ca"
        }, {"name": "square-inc-cash", "hex": "f4cb"}, {
            "name": "square-outline",
            "hex": "f762"
        }, {"name": "square-root", "hex": "f783"}, {"name": "stack-overflow", "hex": "f4cc"}, {
            "name": "stackexchange",
            "hex": "f60b"
        }, {"name": "stadium", "hex": "f71f"}, {"name": "stairs", "hex": "f4cd"}, {
            "name": "standard-definition",
            "hex": "f7ee"
        }, {"name": "star", "hex": "f4ce"}, {"name": "star-circle", "hex": "f4cf"}, {
            "name": "star-half",
            "hex": "f4d0"
        }, {"name": "star-off", "hex": "f4d1"}, {"name": "star-outline", "hex": "f4d2"}, {
            "name": "steam",
            "hex": "f4d3"
        }, {"name": "steering", "hex": "f4d4"}, {"name": "step-backward", "hex": "f4d5"}, {
            "name": "step-backward-2",
            "hex": "f4d6"
        }, {"name": "step-forward", "hex": "f4d7"}, {"name": "step-forward-2", "hex": "f4d8"}, {
            "name": "stethoscope",
            "hex": "f4d9"
        }, {"name": "sticker", "hex": "f5d0"}, {"name": "sticker-emoji", "hex": "f784"}, {
            "name": "stocking",
            "hex": "f4da"
        }, {"name": "stop", "hex": "f4db"}, {"name": "stop-circle", "hex": "f666"}, {
            "name": "stop-circle-outline",
            "hex": "f667"
        }, {"name": "store", "hex": "f4dc"}, {"name": "store-24-hour", "hex": "f4dd"}, {
            "name": "stove",
            "hex": "f4de"
        }, {"name": "subdirectory-arrow-left", "hex": "f60c"}, {
            "name": "subdirectory-arrow-right",
            "hex": "f60d"
        }, {"name": "subway", "hex": "f6ab"}, {"name": "subway-variant", "hex": "f4df"}, {
            "name": "summit",
            "hex": "f785"
        }, {"name": "sunglasses", "hex": "f4e0"}, {
            "name": "surround-sound",
            "hex": "f5c5"
        }, {"name": "surround-sound-2-0", "hex": "f7ef"}, {
            "name": "surround-sound-3-1",
            "hex": "f7f0"
        }, {"name": "surround-sound-5-1", "hex": "f7f1"}, {"name": "surround-sound-7-1", "hex": "f7f2"}, {
            "name": "svg",
            "hex": "f720"
        }, {"name": "swap-horizontal", "hex": "f4e1"}, {"name": "swap-vertical", "hex": "f4e2"}, {
            "name": "swim",
            "hex": "f4e3"
        }, {"name": "switch", "hex": "f4e4"}, {"name": "sword", "hex": "f4e5"}, {
            "name": "sword-cross",
            "hex": "f786"
        }, {"name": "sync", "hex": "f4e6"}, {"name": "sync-alert", "hex": "f4e7"}, {
            "name": "sync-off",
            "hex": "f4e8"
        }, {"name": "tab", "hex": "f4e9"}, {"name": "tab-plus", "hex": "f75b"}, {
            "name": "tab-unselected",
            "hex": "f4ea"
        }, {"name": "table", "hex": "f4eb"}, {
            "name": "table-column",
            "hex": "f834"
        }, {"name": "table-column-plus-after", "hex": "f4ec"}, {
            "name": "table-column-plus-before",
            "hex": "f4ed"
        }, {"name": "table-column-remove", "hex": "f4ee"}, {
            "name": "table-column-width",
            "hex": "f4ef"
        }, {"name": "table-edit", "hex": "f4f0"}, {"name": "table-large", "hex": "f4f1"}, {
            "name": "table-of-contents",
            "hex": "f835"
        }, {"name": "table-row", "hex": "f836"}, {
            "name": "table-row-height",
            "hex": "f4f2"
        }, {"name": "table-row-plus-after", "hex": "f4f3"}, {
            "name": "table-row-plus-before",
            "hex": "f4f4"
        }, {"name": "table-row-remove", "hex": "f4f5"}, {"name": "table-settings", "hex": "f837"}, {
            "name": "tablet",
            "hex": "f4f6"
        }, {"name": "tablet-android", "hex": "f4f7"}, {"name": "tablet-ipad", "hex": "f4f8"}, {
            "name": "taco",
            "hex": "f761"
        }, {"name": "tag", "hex": "f4f9"}, {"name": "tag-faces", "hex": "f4fa"}, {
            "name": "tag-heart",
            "hex": "f68a"
        }, {"name": "tag-multiple", "hex": "f4fb"}, {"name": "tag-outline", "hex": "f4fc"}, {
            "name": "tag-plus",
            "hex": "f721"
        }, {"name": "tag-remove", "hex": "f722"}, {"name": "tag-text-outline", "hex": "f4fd"}, {
            "name": "target",
            "hex": "f4fe"
        }, {"name": "taxi", "hex": "f4ff"}, {"name": "teach", "hex": "f88f"}, {
            "name": "teamviewer",
            "hex": "f500"
        }, {"name": "telegram", "hex": "f501"}, {"name": "television", "hex": "f502"}, {
            "name": "television-box",
            "hex": "f838"
        }, {"name": "television-classic", "hex": "f7f3"}, {
            "name": "television-classic-off",
            "hex": "f839"
        }, {"name": "television-guide", "hex": "f503"}, {
            "name": "television-off",
            "hex": "f83a"
        }, {"name": "temperature-celsius", "hex": "f504"}, {
            "name": "temperature-fahrenheit",
            "hex": "f505"
        }, {"name": "temperature-kelvin", "hex": "f506"}, {"name": "tennis", "hex": "f507"}, {
            "name": "tent",
            "hex": "f508"
        }, {"name": "terrain", "hex": "f509"}, {"name": "test-tube", "hex": "f668"}, {
            "name": "text-shadow",
            "hex": "f669"
        }, {"name": "text-to-speech", "hex": "f50a"}, {"name": "text-to-speech-off", "hex": "f50b"}, {
            "name": "textbox",
            "hex": "f60e"
        }, {"name": "textbox-password", "hex": "f7f4"}, {"name": "texture", "hex": "f50c"}, {
            "name": "theater",
            "hex": "f50d"
        }, {"name": "theme-light-dark", "hex": "f50e"}, {
            "name": "thermometer",
            "hex": "f50f"
        }, {"name": "thermometer-lines", "hex": "f510"}, {
            "name": "thermostat-box",
            "hex": "f890"
        }, {"name": "thought-bubble", "hex": "f7f5"}, {
            "name": "thought-bubble-outline",
            "hex": "f7f6"
        }, {"name": "thumb-down", "hex": "f511"}, {"name": "thumb-down-outline", "hex": "f512"}, {
            "name": "thumb-up",
            "hex": "f513"
        }, {"name": "thumb-up-outline", "hex": "f514"}, {"name": "thumbs-up-down", "hex": "f515"}, {
            "name": "ticket",
            "hex": "f516"
        }, {"name": "ticket-account", "hex": "f517"}, {
            "name": "ticket-confirmation",
            "hex": "f518"
        }, {"name": "ticket-percent", "hex": "f723"}, {"name": "tie", "hex": "f519"}, {
            "name": "tilde",
            "hex": "f724"
        }, {"name": "timelapse", "hex": "f51a"}, {"name": "timer", "hex": "f51b"}, {
            "name": "timer-10",
            "hex": "f51c"
        }, {"name": "timer-3", "hex": "f51d"}, {"name": "timer-off", "hex": "f51e"}, {
            "name": "timer-sand",
            "hex": "f51f"
        }, {"name": "timer-sand-empty", "hex": "f6ac"}, {
            "name": "timer-sand-full",
            "hex": "f78b"
        }, {"name": "timetable", "hex": "f520"}, {"name": "toggle-switch", "hex": "f521"}, {
            "name": "toggle-switch-off",
            "hex": "f522"
        }, {"name": "tooltip", "hex": "f523"}, {"name": "tooltip-edit", "hex": "f524"}, {
            "name": "tooltip-image",
            "hex": "f525"
        }, {"name": "tooltip-outline", "hex": "f526"}, {
            "name": "tooltip-outline-plus",
            "hex": "f527"
        }, {"name": "tooltip-text", "hex": "f528"}, {"name": "tooth", "hex": "f529"}, {
            "name": "tor",
            "hex": "f52a"
        }, {"name": "tower-beach", "hex": "f680"}, {"name": "tower-fire", "hex": "f681"}, {
            "name": "towing",
            "hex": "f83b"
        }, {"name": "trackpad", "hex": "f7f7"}, {"name": "tractor", "hex": "f891"}, {
            "name": "traffic-light",
            "hex": "f52b"
        }, {"name": "train", "hex": "f52c"}, {"name": "tram", "hex": "f52d"}, {
            "name": "transcribe",
            "hex": "f52e"
        }, {"name": "transcribe-close", "hex": "f52f"}, {
            "name": "transfer",
            "hex": "f530"
        }, {"name": "transit-transfer", "hex": "f6ad"}, {"name": "translate", "hex": "f5ca"}, {
            "name": "treasure-chest",
            "hex": "f725"
        }, {"name": "tree", "hex": "f531"}, {"name": "trello", "hex": "f532"}, {
            "name": "trending-down",
            "hex": "f533"
        }, {"name": "trending-neutral", "hex": "f534"}, {"name": "trending-up", "hex": "f535"}, {
            "name": "triangle",
            "hex": "f536"
        }, {"name": "triangle-outline", "hex": "f537"}, {"name": "trophy", "hex": "f538"}, {
            "name": "trophy-award",
            "hex": "f539"
        }, {"name": "trophy-outline", "hex": "f53a"}, {
            "name": "trophy-variant",
            "hex": "f53b"
        }, {"name": "trophy-variant-outline", "hex": "f53c"}, {
            "name": "truck",
            "hex": "f53d"
        }, {"name": "truck-delivery", "hex": "f53e"}, {"name": "truck-fast", "hex": "f787"}, {
            "name": "truck-trailer",
            "hex": "f726"
        }, {"name": "tshirt-crew", "hex": "f53f"}, {"name": "tshirt-v", "hex": "f540"}, {
            "name": "tumblr",
            "hex": "f541"
        }, {"name": "tumblr-reblog", "hex": "f542"}, {"name": "tune", "hex": "f62e"}, {
            "name": "tune-vertical",
            "hex": "f66a"
        }, {"name": "twitch", "hex": "f543"}, {"name": "twitter", "hex": "f544"}, {
            "name": "twitter-box",
            "hex": "f545"
        }, {"name": "twitter-circle", "hex": "f546"}, {"name": "twitter-retweet", "hex": "f547"}, {
            "name": "uber",
            "hex": "f748"
        }, {"name": "ubuntu", "hex": "f548"}, {"name": "ultra-high-definition", "hex": "f7f8"}, {
            "name": "umbraco",
            "hex": "f549"
        }, {"name": "umbrella", "hex": "f54a"}, {"name": "umbrella-outline", "hex": "f54b"}, {
            "name": "undo",
            "hex": "f54c"
        }, {"name": "undo-variant", "hex": "f54d"}, {
            "name": "unfold-less-horizontal",
            "hex": "f54e"
        }, {"name": "unfold-less-vertical", "hex": "f75f"}, {
            "name": "unfold-more-horizontal",
            "hex": "f54f"
        }, {"name": "unfold-more-vertical", "hex": "f760"}, {"name": "ungroup", "hex": "f550"}, {
            "name": "unity",
            "hex": "f6ae"
        }, {"name": "untappd", "hex": "f551"}, {"name": "update", "hex": "f6af"}, {
            "name": "upload",
            "hex": "f552"
        }, {"name": "upload-multiple", "hex": "f83c"}, {"name": "upload-network", "hex": "f6f5"}, {
            "name": "usb",
            "hex": "f553"
        }, {"name": "van-passenger", "hex": "f7f9"}, {"name": "van-utility", "hex": "f7fa"}, {
            "name": "vanish",
            "hex": "f7fb"
        }, {"name": "vector-arrange-above", "hex": "f554"}, {
            "name": "vector-arrange-below",
            "hex": "f555"
        }, {"name": "vector-circle", "hex": "f556"}, {
            "name": "vector-circle-variant",
            "hex": "f557"
        }, {"name": "vector-combine", "hex": "f558"}, {
            "name": "vector-curve",
            "hex": "f559"
        }, {"name": "vector-difference", "hex": "f55a"}, {
            "name": "vector-difference-ab",
            "hex": "f55b"
        }, {"name": "vector-difference-ba", "hex": "f55c"}, {
            "name": "vector-ellipse",
            "hex": "f892"
        }, {"name": "vector-intersection", "hex": "f55d"}, {
            "name": "vector-line",
            "hex": "f55e"
        }, {"name": "vector-point", "hex": "f55f"}, {
            "name": "vector-polygon",
            "hex": "f560"
        }, {"name": "vector-polyline", "hex": "f561"}, {
            "name": "vector-radius",
            "hex": "f749"
        }, {"name": "vector-rectangle", "hex": "f5c6"}, {
            "name": "vector-selection",
            "hex": "f562"
        }, {"name": "vector-square", "hex": "f001"}, {
            "name": "vector-triangle",
            "hex": "f563"
        }, {"name": "vector-union", "hex": "f564"}, {"name": "venmo", "hex": "f578"}, {
            "name": "verified",
            "hex": "f565"
        }, {"name": "vibrate", "hex": "f566"}, {"name": "video", "hex": "f567"}, {
            "name": "video-3d",
            "hex": "f7fc"
        }, {"name": "video-4k-box", "hex": "f83d"}, {
            "name": "video-input-antenna",
            "hex": "f83e"
        }, {"name": "video-input-component", "hex": "f83f"}, {
            "name": "video-input-hdmi",
            "hex": "f840"
        }, {"name": "video-input-svideo", "hex": "f841"}, {"name": "video-off", "hex": "f568"}, {
            "name": "video-switch",
            "hex": "f569"
        }, {"name": "view-agenda", "hex": "f56a"}, {"name": "view-array", "hex": "f56b"}, {
            "name": "view-carousel",
            "hex": "f56c"
        }, {"name": "view-column", "hex": "f56d"}, {
            "name": "view-dashboard",
            "hex": "f56e"
        }, {"name": "view-dashboard-variant", "hex": "f842"}, {"name": "view-day", "hex": "f56f"}, {
            "name": "view-grid",
            "hex": "f570"
        }, {"name": "view-headline", "hex": "f571"}, {"name": "view-list", "hex": "f572"}, {
            "name": "view-module",
            "hex": "f573"
        }, {"name": "view-parallel", "hex": "f727"}, {"name": "view-quilt", "hex": "f574"}, {
            "name": "view-sequential",
            "hex": "f728"
        }, {"name": "view-stream", "hex": "f575"}, {"name": "view-week", "hex": "f576"}, {
            "name": "vimeo",
            "hex": "f577"
        }, {"name": "violin", "hex": "f60f"}, {"name": "virtual-reality", "hex": "f893"}, {
            "name": "visualstudio",
            "hex": "f610"
        }, {"name": "vk", "hex": "f579"}, {"name": "vk-box", "hex": "f57a"}, {
            "name": "vk-circle",
            "hex": "f57b"
        }, {"name": "vlc", "hex": "f57c"}, {"name": "voice", "hex": "f5cb"}, {
            "name": "voicemail",
            "hex": "f57d"
        }, {"name": "volume-high", "hex": "f57e"}, {"name": "volume-low", "hex": "f57f"}, {
            "name": "volume-medium",
            "hex": "f580"
        }, {"name": "volume-minus", "hex": "f75d"}, {"name": "volume-mute", "hex": "f75e"}, {
            "name": "volume-off",
            "hex": "f581"
        }, {"name": "volume-plus", "hex": "f75c"}, {"name": "vpn", "hex": "f582"}, {
            "name": "vuejs",
            "hex": "f843"
        }, {"name": "walk", "hex": "f583"}, {"name": "wall", "hex": "f7fd"}, {
            "name": "wallet",
            "hex": "f584"
        }, {"name": "wallet-giftcard", "hex": "f585"}, {
            "name": "wallet-membership",
            "hex": "f586"
        }, {"name": "wallet-travel", "hex": "f587"}, {"name": "wan", "hex": "f588"}, {
            "name": "washing-machine",
            "hex": "f729"
        }, {"name": "watch", "hex": "f589"}, {"name": "watch-export", "hex": "f58a"}, {
            "name": "watch-export-variant",
            "hex": "f894"
        }, {"name": "watch-import", "hex": "f58b"}, {
            "name": "watch-import-variant",
            "hex": "f895"
        }, {"name": "watch-variant", "hex": "f896"}, {"name": "watch-vibrate", "hex": "f6b0"}, {
            "name": "water",
            "hex": "f58c"
        }, {"name": "water-off", "hex": "f58d"}, {"name": "water-percent", "hex": "f58e"}, {
            "name": "water-pump",
            "hex": "f58f"
        }, {"name": "watermark", "hex": "f612"}, {"name": "waves", "hex": "f78c"}, {
            "name": "weather-cloudy",
            "hex": "f590"
        }, {"name": "weather-fog", "hex": "f591"}, {
            "name": "weather-hail",
            "hex": "f592"
        }, {"name": "weather-hurricane", "hex": "f897"}, {
            "name": "weather-lightning",
            "hex": "f593"
        }, {"name": "weather-lightning-rainy", "hex": "f67d"}, {
            "name": "weather-night",
            "hex": "f594"
        }, {"name": "weather-partlycloudy", "hex": "f595"}, {
            "name": "weather-pouring",
            "hex": "f596"
        }, {"name": "weather-rainy", "hex": "f597"}, {
            "name": "weather-snowy",
            "hex": "f598"
        }, {"name": "weather-snowy-rainy", "hex": "f67e"}, {
            "name": "weather-sunny",
            "hex": "f599"
        }, {"name": "weather-sunset", "hex": "f59a"}, {
            "name": "weather-sunset-down",
            "hex": "f59b"
        }, {"name": "weather-sunset-up", "hex": "f59c"}, {
            "name": "weather-windy",
            "hex": "f59d"
        }, {"name": "weather-windy-variant", "hex": "f59e"}, {"name": "web", "hex": "f59f"}, {
            "name": "webcam",
            "hex": "f5a0"
        }, {"name": "webhook", "hex": "f62f"}, {"name": "webpack", "hex": "f72a"}, {
            "name": "wechat",
            "hex": "f611"
        }, {"name": "weight", "hex": "f5a1"}, {"name": "weight-kilogram", "hex": "f5a2"}, {
            "name": "whatsapp",
            "hex": "f5a3"
        }, {"name": "wheelchair-accessibility", "hex": "f5a4"}, {
            "name": "white-balance-auto",
            "hex": "f5a5"
        }, {"name": "white-balance-incandescent", "hex": "f5a6"}, {
            "name": "white-balance-iridescent",
            "hex": "f5a7"
        }, {"name": "white-balance-sunny", "hex": "f5a8"}, {"name": "widgets", "hex": "f72b"}, {
            "name": "wifi",
            "hex": "f5a9"
        }, {"name": "wifi-off", "hex": "f5aa"}, {"name": "wii", "hex": "f5ab"}, {
            "name": "wiiu",
            "hex": "f72c"
        }, {"name": "wikipedia", "hex": "f5ac"}, {"name": "window-close", "hex": "f5ad"}, {
            "name": "window-closed",
            "hex": "f5ae"
        }, {"name": "window-maximize", "hex": "f5af"}, {
            "name": "window-minimize",
            "hex": "f5b0"
        }, {"name": "window-open", "hex": "f5b1"}, {"name": "window-restore", "hex": "f5b2"}, {
            "name": "windows",
            "hex": "f5b3"
        }, {"name": "wordpress", "hex": "f5b4"}, {"name": "worker", "hex": "f5b5"}, {
            "name": "wrap",
            "hex": "f5b6"
        }, {"name": "wrench", "hex": "f5b7"}, {"name": "wunderlist", "hex": "f5b8"}, {
            "name": "xamarin",
            "hex": "f844"
        }, {"name": "xamarin-outline", "hex": "f845"}, {"name": "xaml", "hex": "f673"}, {
            "name": "xbox",
            "hex": "f5b9"
        }, {"name": "xbox-controller", "hex": "f5ba"}, {
            "name": "xbox-controller-battery-alert",
            "hex": "f74a"
        }, {"name": "xbox-controller-battery-empty", "hex": "f74b"}, {
            "name": "xbox-controller-battery-full",
            "hex": "f74c"
        }, {"name": "xbox-controller-battery-low", "hex": "f74d"}, {
            "name": "xbox-controller-battery-medium",
            "hex": "f74e"
        }, {"name": "xbox-controller-battery-unknown", "hex": "f74f"}, {
            "name": "xbox-controller-off",
            "hex": "f5bb"
        }, {"name": "xda", "hex": "f5bc"}, {"name": "xing", "hex": "f5bd"}, {
            "name": "xing-box",
            "hex": "f5be"
        }, {"name": "xing-circle", "hex": "f5bf"}, {"name": "xml", "hex": "f5c0"}, {
            "name": "xmpp",
            "hex": "f7fe"
        }, {"name": "yammer", "hex": "f788"}, {"name": "yeast", "hex": "f5c1"}, {
            "name": "yelp",
            "hex": "f5c2"
        }, {"name": "yin-yang", "hex": "f67f"}, {
            "name": "youtube-creator-studio",
            "hex": "f846"
        }, {"name": "youtube-gaming", "hex": "f847"}, {"name": "youtube-play", "hex": "f5c3"}, {
            "name": "youtube-tv",
            "hex": "f448"
        }, {"name": "zip-box", "hex": "f5c4"}];
        icons.push({"name": "blank", "hex": "f68c"});
        var copyText = function (text) {
            var copyFrom = document.createElement('textarea');
            copyFrom.setAttribute("style", "position:fixed;opacity:0;top:100px;left:100px;");
            copyFrom.value = text;
            document.body.appendChild(copyFrom);
            copyFrom.select();
            document.execCommand('copy');
            var copied = document.createElement('div');
            copied.setAttribute('class', 'copied');
            copied.appendChild(document.createTextNode('Copied to Clipboard'));
            document.body.appendChild(copied);
            setTimeout(function () {
                document.body.removeChild(copyFrom);
                document.body.removeChild(copied);
            }, 1500);
        };
        for (var j = 0; j < icons.length; j++) {
            var div = document.createElement('div'),
                i = document.createElement('i');
            i.className = 'mdi mdi-' + icons[j].name;
            div.appendChild(i);
            var code = document.createElement('code');
            code.appendChild(document.createTextNode(icons[j].hex));
            div.appendChild(code);
            var span = document.createElement('span');
            span.appendChild(document.createTextNode('mdi-' + icons[j].name));
            div.appendChild(span);
            span.onclick = (function (icon) {
                return function () {
                    callbackPreviewIcon('mdi mdi-' + icon.name);
                };
            })(icons[j]);
            document.getElementById('icons').appendChild(div);
        }
    })();
</script>
