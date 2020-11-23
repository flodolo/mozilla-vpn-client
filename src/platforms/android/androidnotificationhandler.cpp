/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "platforms/android/androidnotificationhandler.h"
#include <jni.h>
#include <QAndroidJniEnvironment>
#include <QAndroidJniObject>
#include <QtAndroid>

AndroidNotificationHandler::AndroidNotificationHandler(QObject *parent)
    : NotificationHandler(parent)
{}

void AndroidNotificationHandler::notify(const QString &title, const QString &message, int timerSec)
{
    QAndroidJniObject jTitle = QAndroidJniObject::fromString(title);
    QAndroidJniObject jMessage = QAndroidJniObject::fromString(message);
    jint jTimerSec = (jint) timerSec;

    QAndroidJniObject::callStaticMethod<void>("com/mozilla/vpn/NotificationUtil",
                                              "update",
                                              "(Ljava/lang/String;Ljava/lang/String;I)V",
                                              jTitle.object(), jMessage.object(), jTimerSec);
}
