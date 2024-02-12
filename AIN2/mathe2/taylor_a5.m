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

%% Integrale berechnen
clear;

%% Cosinus
I = [-pi, pi];
DIM = 100;
x = linspace(I(1), I(2), DIM);
N = 6;

% Definition der Cosinus-Polynomfunktion
Tf_cos = 0;
for k = 0:N
    Tf_cos = Tf_cos + (-1)^k * x.^(2*k) / factorial(2*k);
end

% Berechnung des Integrals
cos_result = integral(@(x) Tf_cos, 0, I(2), 'ArrayValued', true);

% Plotten von cos(x), Taylorpolynom und integriertem Verlauf
figure;
plot(x, cos_result, '-m');
grid on
title('Integrierter Verlauf');
xlabel('x');
ylim([-5, 5]);  % Manuelle Anpassung der y-Limits


%% Sinus
I = [-pi, pi];
DIM = 100;
x = linspace(I(1), I(2), DIM);
N = 18;
Tf_sin = 0;
for k = 0:N
    Tf_sin = Tf_sin + (-1)^k * x.^(2*k+2) / factorial(2*k+2);
end

% Berechnung des Integrals
sin_result = integral(@(x) Tf_sin, 0, I(2), 'ArrayValued', true);

% Plotten von sin(x), Taylorpolynom und integriertem Verlauf
figure;
plot(x, sin_result, '-m');
grid on
title('Integrierter Verlauf');
xlabel('x');
ylim([-5, 5]);  % Manuelle Anpassung der y-Limits

%% Parametisierung
 
clear;

% Parameter
A = 2/3;
T = 9/4;

% Definition der Integralfunktionen
syms s;
C_s = int(cos((pi/2) * s^2), s);
S_s = int(sin((pi/2) * s^2), s);

% Parametrische Darstellung
x = A * sqrt(pi) * C_s;
y = A * sqrt(pi) * S_s;

% Parametrisierung für T
t_values = linspace(0, T, 1000);
x_values = double(subs(x, s, t_values));
y_values = double(subs(y, s, t_values));

% Plotten
figure;
plot(x_values, y_values, '-r');
grid on;
xlabel('x');
ylabel('y');

%% Passendes T und Abbildung
clear;

% Parameter
A = 1.2;
T = 1.5;
b = [-1; 0.9];

% Definition der Integralfunktionen
syms s;
C_s = int(cos((pi/2) * s^2), s);
S_s = int(sin((pi/2) * s^2), s);

% Parametrische Darstellung

x = A * (A * sqrt(pi) * C_s) + b(1);
y = A * (A * sqrt(pi) * S_s) + b(2);

% Parametrisierung für T
t_values = linspace(0, T, 1000);
x_values = double(subs(x, s, t_values));
y_values = double(subs(y, s, t_values));

% Plotten
figure;
plot(x_values, y_values, '-r');
grid on;
xlabel('x');
ylabel('y');






