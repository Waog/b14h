# !/usr/bin/env python
__author__ = 'gx'

import sys
import time

from lib.lcd import LCDSysInfo, BackgroundColours, TextColours, TextLines


lcd = LCDSysInfo()
lcd.set_text_background_colour(BackgroundColours.BLACK)
lcd.set_brightness(255)
lcd.dim_when_idle(False)

while True:
    line = sys.stdin.readline().strip()

    if not line:
        time.sleep(0.1)
        continue
    lcd.clear_lines(TextLines.ALL, BackgroundColours.BLACK)
    time.sleep(0.1)
    lcd.display_text_anywhere(0, 0, line[0:100], TextColours.GREEN)
    time.sleep(0.1)

