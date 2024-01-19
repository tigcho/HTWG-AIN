#! /bin/sh
for s in benotung fachnote fachnoten_liste ; do
  command="g++ -g -c -Wall -Wextra -Werror -std=c++11 -pedantic $s.cpp"
  echo "Source File: $s.cpp"
  echo $command
  eval $command
  if [ $? -ne 0 ] ; then
    echo build failed
    exit 1
  fi
done

link_command="ar rvs libaufgabe6.a benotung.o fachnote.o fachnoten_liste.o"
echo $link_command
eval $link_command
if [ $? -ne 0 ] ; then
  echo build failed
  exit 1
fi
echo build successful