/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import QtQuick 2.15
import QtQuick.Controls 2.5
import QtGraphicalEffects 1.0
import QtQuick.Layouts 1.11

import "../themes/themes.js" as Theme

// VPNLink
Control {
    id: link
    font.pixelSize: Theme.fontSize
    font.family: vpnFontInter.name
    property alias color: linkText.color
    required property var text
    signal clicked

    contentItem: Text {
        id: linkText
        text: link.text
        font: link.font

        color: {
            if (mouseArea.pressed) {
                return Theme.bluePressed
            }
            if (mouseArea.containsMouse) {
                return Theme.blueHovered
            }
            return Theme.blue
        }

        Behavior on color {
            ColorAnimation {
                duration: 200
            }
        }

        MouseArea {
            id: mouseArea
            anchors.fill: parent
            onClicked: link.clicked()
            cursorShape: "PointingHandCursor"
            hoverEnabled: true
        }
    }

    background: Rectangle {
        radius: 4
        color: Theme.bgColor
        border.width: activeFocus ? 2 : 0
        border.color: Theme.blueFocusStroke
        Accessible.name: link.text
        Accessible.role: Accessible.Link
        Accessible.onPressAction: link.clicked()
        Keys.onSpacePressed: link.clicked()
        Keys.onReturnPressed: link.clicked()
        activeFocusOnTab: true
    }
}
