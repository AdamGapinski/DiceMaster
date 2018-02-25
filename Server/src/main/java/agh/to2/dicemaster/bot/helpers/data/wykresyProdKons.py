import matplotlib.pyplot as plt
# plt.plot([1,2,3,4], [1,4,9,16], 'ro')
# plt.axis([0, 6, 0, 20])


def showFirstGraph():
    argumentsBufferDelay = [0, 500, 650, 800, 1000, 1250, 1500, 1750, 2000, 2500, 3000, 4000, 5000]
    asynchronousElements = [524841, 278057, 220699, 190710, 172263, 152585, 151618, 122740, 126283, 100026, 76413,
                            76084, 51307]

    activeObjectElements = [262526, 258603, 206222, 217917, 177690, 185552, 171412, 184997, 153852, 144187, 179933,
                            179956, 178279]
    fig = plt.figure()
    fig.suptitle('Active object i asynchroniczny od obciążenia bufora', fontsize=14, fontweight='bold')

    ax = fig.add_subplot(111)

    ax.set_xlabel('Obciążenie bufora[ms]')
    ax.set_ylabel('Ilość wyprodukowanych elementów')

    ax.text(3000, 200000, 'Active object')
    ax.text(3000, 100000, 'Asynchroniczny')

    ax.plot(argumentsBufferDelay, asynchronousElements, 'ro')
    ax.plot(argumentsBufferDelay, activeObjectElements, 'bo')

    ax.plot(argumentsBufferDelay, asynchronousElements)
    ax.plot(argumentsBufferDelay, activeObjectElements)

    plt.show()


def showSecondGraph():
    producerThreadsNumber = [20, 50, 100, 150, 200, 250, 300, 400, 500, 600]
    asynchronousMade = [213004, 525936, 1039700, 1568077, 2045668, 2108933, 2137073, 2182758, 2193491, 2192078]
    activeObjectMade = [330179, 289308, 292094, 328177, 199345, 324273, 235990, 315355, 221255, 252950]
    activeObjectQueued = [1178711, 2546217, 5294780, 7467018, 10111815, 13004591, 15630158, 20536032, 25684069, 30477968]



    fig = plt.figure()
    fig.suptitle('Active object i asynchroniczny od ilości wątków', fontsize=14, fontweight='bold')

    ax = fig.add_subplot(111)

    ax.set_xlabel('Ilość wątków jednego rodzaju')
    ax.set_ylabel('Ilość wyprodukowanych elementów')

    ax.text(400, 400000, 'Active object')
    ax.text(160, 1500000, 'Asynchroniczny')


    ax.plot(producerThreadsNumber, asynchronousMade)
    ax.plot(producerThreadsNumber, asynchronousMade,'ro')
    ax.plot(producerThreadsNumber, activeObjectMade)
    ax.plot(producerThreadsNumber, activeObjectMade,'bo')
    ax.plot(producerThreadsNumber, activeObjectQueued)
    ax.plot(producerThreadsNumber, activeObjectQueued,'go')


    plt.show()


# showFirstGraph()
showSecondGraph()


# stałą wartość produkcji i konsumpcji, raczej niewielką np. 100
# zmierzyć w międzyczasie wykonana dodatkoœą pracę
# uwzględniać wszystkie pozostałe parametry
# WSZYSTKIE parametry stałe też uwzględniać na wykresach
#
# 5.0 warunkowo: wrzucić do moodle: kod i sprawozdanie (wykresy z wnioskami)