clear all
close all

I = [-4,0.9];
N1 = 2;
N2 = 14;
x0 = -1;
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
ylim([0,20])

%%
function y=Dfunc(order,x)

syms s
f(s) = 1./(1-s); %exp(s.^2);

for l=1:order
    f(s) = diff(f(s),s);
end

y = subs(f(s),s=x);

end
