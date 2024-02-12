clear all
close all

%% f = cos(x)

I = [-5*pi,5*pi];
N = 14;
DIM = 100;
x = linspace(I(1),I(2),DIM);

Tf = 0;
for k=0:N
    Tf = Tf + (-1)^k*x.^(2*k)/factorial(2*k);
end

%%

plot(x,cos(x),'k-');
grid on
hold on
plot(x,Tf,'r-');
ylim([-2,2])