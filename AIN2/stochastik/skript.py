import numpy as np
from scipy import stats
import matplotlib.pyplot as plt

BLUE = '\033[94m'
END = '\033[0m'

# Univariate Statistik
print(BLUE + "Univariate Statistik" + END)

G = np.array([206, 217, 227, 222, 222, 228, 232])

# Modalwert
mode = stats.mode(G)
print("Modalwert(e):", mode[0])

# Mittelwert
mean = np.mean(G)
print("Mittelwert", mean)

# Quantil
q95 = np.quantile(G, 0.95)
print("Quantil", q95)

# Standartabweichung
std = np.std(G, ddof=1)
print("Standartabweichung", std)

# Varianz
var = np.var(G, ddof=1)
print("Varianz", var)

# Multivariate Statistik
print(BLUE + "Multivariate Statistik" + END)

L = np.array([64, 66, 79, 75, 81, 70, 83])
T = np.array([94, 89, 90, 91, 89, 89, 94])

# Scatterplot
plt.scatter(L, T)
plt.show()

# Korrelationskoeffizient
corr = np.corrcoef(L, T)
print("Korrelationskoeffizient", corr[1][0])

# Zufallsvariablen

E = np.array([2, 3, 4, 5, 6])
P = np.array([0.1, 0.2, 0.4, 0.2, 0.1])

# Erwarungswert
print("Erwartungswert", (E * P).sum())

# Binoimalverteilung
print(BLUE + "Binomialverteilung" + END)
n = 10
p = 0.5
k = 5

print("P(X = k)")
print(stats.binom.pmf(k, n, p))
print("P(X <= k)")
print(stats.binom.cdf(k, n, p))
print("P(X >= k)")
print(1 - stats.binom.cdf(k - 1, n, p))
print("P(X < k)")
print(stats.binom.cdf(k - 1, n, p))
print("P(X > k)")
print(1 - stats.binom.cdf(k, n, p))

# Normalverteilung
print(BLUE + "Normalverteilung" + END)
mu = 0
sigma = 1
x = 1

print("P(X = x)")
print(stats.norm.pdf(x, mu, sigma))
print("P(X <= x)")
print(stats.norm.cdf(x, mu, sigma))
print("P(X >= x)")
print(1 - stats.norm.cdf(x, mu, sigma))
print("P(X < x)")
print(stats.norm.cdf(x, mu, sigma))
print("P(X > x)")
print(1 - stats.norm.cdf(x, mu, sigma))

# Exponentialverteilung
print(BLUE + "Exponentialverteilung" + END)
lamb = 1
x = 1

print("P(X = x)")
print(stats.expon.pdf(x, 0, lamb))
print("P(X <= x)")
print(stats.expon.cdf(x, 0, lamb))
print("P(X >= x)")
print(1 - stats.expon.cdf(x, 0, lamb))
print("P(X < x)")
print(stats.expon.cdf(x, 0, lamb))
print("P(X > x)")
print(1 - stats.expon.cdf(x, 0, lamb))

# Poissonverteilung
print(BLUE + "Poissonverteilung" + END)
lamb = 1
x = 1

print("P(X = x)")
print(stats.poisson.pmf(x, lamb))
print("P(X <= x)")
print(stats.poisson.cdf(x, lamb))
print("P(X >= x)")
print(1 - stats.poisson.cdf(x - 1, lamb))
print("P(X < x)")
print(stats.poisson.cdf(x - 1, lamb))
print("P(X > x)")
print(1 - stats.poisson.cdf(x, lamb))

# geomethrische Verteilung
print(BLUE + "geometrische Verteilung" + END)
p = 0.5
x = 1

print("P(X = x)")
print(stats.geom.pmf(x, p))
print("P(X <= x)")
print(stats.geom.cdf(x, p))
print("P(X >= x)")
print(1 - stats.geom.cdf(x - 1, p))
print("P(X < x)")
print(stats.geom.cdf(x - 1, p))
print("P(X > x)")
print(1 - stats.geom.cdf(x, p))

# Bernoulliverteilung
print(BLUE + "Bernoulliverteilung" + END)
p = 0.5
x = 1

print("P(X = x)")
print(stats.bernoulli.pmf(x, p))
print("P(X <= x)")
print(stats.bernoulli.cdf(x, p))
print("P(X >= x)")
print(1 - stats.bernoulli.cdf(x - 1, p))
print("P(X < x)")
print(stats.bernoulli.cdf(x - 1, p))
print("P(X > x)")
print(1 - stats.bernoulli.cdf(x, p))

# Gleichverteilung
print(BLUE + "Gleichverteilung" + END)
a = 0
b = 1
x = 0.5

print("P(X = x)")
print(stats.uniform.pdf(x, a, b))
print("P(X <= x)")
print(stats.uniform.cdf(x, a, b))
print("P(X >= x)")
print(1 - stats.uniform.cdf(x, a, b))
print("P(X < x)")
print(stats.uniform.cdf(x, a, b))
print("P(X > x)")
print(1 - stats.uniform.cdf(x, a, b))
