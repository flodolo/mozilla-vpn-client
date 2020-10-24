/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "testserverdata.h"
#include "../src/serverdata.h"
#include "autotest.h"

void TestServerData::basic()
{
    ServerData sd;

    QCOMPARE(sd.initialized(), false);
    QCOMPARE(sd.countryCode(), "");
    QCOMPARE(sd.city(), "");
}

DECLARE_TEST(TestServerData)