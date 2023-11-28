#! /usr/bin/env python3

traceFile = open('./ls-trace.txt', 'r')
vpnFile = open('./vpn.txt', 'w')
x = 0
for line in traceFile:
    if (line.startswith("I ") or line.startswith(" ")):
        vpnFile.write(str((int("0x" + line[3:11], 16) & 0xfffff000) >> 12) + "\n")

traceFile.close()
vpnFile.close()