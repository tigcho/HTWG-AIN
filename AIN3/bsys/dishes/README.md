# Dishes

### Scheduler

Unser Scheduler ist ein einfacher Round-Robin-Scheduler, der mit Prioritäten arbeitet. Ein Round-Robin-Scheduler teilt jedem Prozess ein festes Zeitquantum zu, in dem er ausgeführt wird. Nach Ablauf des Zeitquantums wird der nächste Prozess in der Warteschlange gestellt. In unserem Fall haben wir die Tierarten (Hunde, Katzen und Mäuse), die in einer bestimmten Reihenfolge  gefüttert werden. Auf diese Weise bekommt jedes Tier eine gerechte Chance gefüttert zu werden.

### Thread-Synchronisation

Die Synchronisation der Threads wird in unserem Programm durch eine Barrier und eine Condition hergestellt. Es stellt sicher, dass  keine Tiere gleichzeitig fressen oder zu anderem ungewollten Verhalten führen könnte.

```python
barrier = Barrier(args.cn + args.dn + args.mn + 1)
```

Die Barrier synchronisiert alle Tier-Threads, sodass sie gleichzeitig starten können. Die waiting()-Funktion synchronisiert die Threads während der Execution und stellt sicher, dass entweder alle Hunde oder alle Katzen mit dem Essen fertig sind, bevor die nächsten Tiere essen dürfen.

### Wie wir mit Tieren umgehen

Das Konzept der Zustände (States) der Tiere (STRAVING, SATISFIED, DEAD) ist ein einfaches Modell der Prozesszustände in einem Betriebssystem (z.B. Running, Waiting, Terminated). Diese Zustände helfen dabei, die Lebensdauer der Tiere zu verwalten und sicherzustellen, dass das Scheduling korrekt durchgeführt wird.

```python

def waiting():
    while any([c.get_status() == State.EATING for c in cats]) \
            and any([d.get_status() == State.EATING for d in dogs]):
        pass
```

Hier wird sichergestellt, dass keine Katze frisst, während ein Hund frisst, was eine Form der wechselseitigen Ausschließung (mutual exclusion) darstellt. Diese Konzepte sind wichtig, um Deadlocks und Race Conditions zu vermeiden.

### Wann sterben die Viecher

Die Parameter, die beim Starten des Programms übergeben werden, bestimmen, wie lange ein Tier ohne Nahrung überleben kann, bevor es stirbt. Diese Parameter modellieren eine Art von "Lebenszyklus" der Prozesse, ähnlich wie in einem Betriebssystem die Lebenszyklen von Prozessen verwaltet werden müssen, um Ressourcen effizient zu nutzen und sicherzustellen, dass alle Prozesse faire Chancen haben.
