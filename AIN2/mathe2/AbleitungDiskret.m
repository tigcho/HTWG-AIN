clear all
close all

I = [0,2*pi];
N = 100;
f = @(x) sin(x);

%%
h = (I(2)-I(1))/(N-1);


xx = I(1):h:I(2);
ff = f(xx)';

df = Diff(ff,h,1);
d2f = Diff(ff,h,2);

%%
fig = figure(1);
plot(xx,ff,'ko')
hold on
grid on
plot(xx,df,'mo')
plot(xx,d2f,'ro')
xx2 = I(1):h/10:I(2);
plot(xx2,cos(xx2),'m-')
xlabel('x')
ylabel('y')
set(gca, 'XAxisLocation', 'origin', 'YAxisLocation', 'origin')
legend('f','df','d2f')
print(fig,'-dpng','Ergebnisbild_AbleitungDiskret.png');

%%
function df = Diff(f,h,order)

N = length(f);

switch order
    case 1
        % gemittelter Differenzenquotient
        D=diag(ones(1,N-1),1)-diag(ones(1,N-1),-1);
        % Vorwärtsdifferenzenquotient am linken Rand
        D(1,1)=-2;
        D(1,2)=2;
        % Rückwärtsdifferenzenquotient am rechten Rand
        D(N,N)=2;
        D(N,N-1)=-2;

        D = D/2/h;
    case 2
        D=-2*diag(ones(1,N))+diag(ones(1,N-1),-1)+diag(ones(1,N-1),1);
        D(1,:)=zeros(1,N);
        D(N,:)=zeros(1,N);
 
        D = D/h/h;
    otherwise
        df=f;
end

df = D*f;

end

