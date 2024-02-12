clear all
close all

%% f = 1/(1-x), Df = R\{1}

I = [-4,0.9];
N = 20;
x0 = -1;
DIM = 100;
x = linspace(I(1),I(2),DIM);

Tf = 0;
for k=0:N
    Tf = Tf + (x-x0).^k/(1-x0)^(k+1);
end

%%

plot(x,1./(1-x),'k-');
grid on
hold on
plot(x,Tf,'r-');
ylim([0,20])
