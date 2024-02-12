clear all
close all

%% Taylorpolynom der Kosinusfunktion
I = [-pi, pi];
DIM = 100;
x = linspace(I(1), I(2), DIM);
N = 4;

Tf_cos = 0;
for k = 0:N
    Tf_cos = Tf_cos + (-1)^k * x.^(4*k) / factorial(2*k);
end

%% Plotten von cos(x^2) und Taylorpolynom
figure;
plot(x, cos(x.^2), '-b');
hold on
plot(x, Tf_cos, '-r');
grid on
ylim([-2, 2])

%% Taylorpolynom der Sinusfunktion
I = [-pi, pi];
DIM = 100;
x = linspace(I(1), I(2), DIM);
N = 18;
Tf_sin = 0;
for k = 0:N
    Tf_sin = Tf_sin + (-1)^k * x.^(4*k+2) / factorial(2*k+1);
end

%% Plotten von sin(x^2) und Taylorpolynom
figure;
plot(x, sin(x.^2), '-b');
hold on
plot(x, Tf_sin, '-r');
grid on
ylim([-2, 2])











