# !/usr/bin/env python
__author__ = 'gx'

import sys
import time

from lib.lcd import LCDSysInfo, BackgroundColours, TextLines, TextAlignment, TextColours


lcd = LCDSysInfo()
lcd.set_text_background_colour(BackgroundColours.BLACK)
lcd.set_brightness(255)
lcd.dim_when_idle(False)

lines = []

while True:
    line = sys.stdin.readline().strip()

    if not line:
        time.sleep(0.1)
        continue

    lines.append(line)
    if len(lines) > 6:
        lines.pop(0)

    for i, l in enumerate(lines):
        lcd.display_text_on_line(i+1, l, pad_for_icon=False, alignment=TextAlignment.LEFT, colour=TextColours.GREEN)