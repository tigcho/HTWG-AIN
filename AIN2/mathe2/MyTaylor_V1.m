clear all
close all

I = [-1,1];
N1 = 2;
N2 = 4;
x0 = 0.5;
DIM = 100;
x = linspace(I(1),I(2),DIM);

Tf1 = 0;
for k=0:N1
    Tf1 = Tf1 + Dfunc(k,x0)/factorial(k)*(x-x0).^k;
end

Tf2 = 0;
for k=0:N2
    Tf2 = Tf2 + Dfunc(k,x0)/factorial(k)*(x-x0).^k;
end

%%

plot(x,Dfunc(0,x),'k-');
grid on
hold on
plot(x,Tf1,'r-');
plot(x,Tf2,'m-');

%%
function y=Dfunc(order,x)

switch order
    case 0
        y = exp(x.^2);
    case 1
        y = 2*x*exp(x^2);
    case 2
        y = 2*exp(x^2) + 4*x^2*exp(x^2);
    case 3
        y = 12*x*exp(x^2) + 8*x^3*exp(x^2);
    case 4
        y = 12*exp(x^2) + 48*x^2*exp(x^2) + 16*x^4*exp(x^2);
    otherwise
        y = 0;
end

end
