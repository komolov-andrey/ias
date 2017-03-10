# Упражнение 2
# Комолов А.В., Вариант 4

# загрузка пакетов
library('data.table')

# загружаем файл с данными по импорту масла в РФ (из прошлой практики)
fileURL <- 'https://raw.githubusercontent.com/aksyuk/R-data/master/COMTRADE/040510-Imp-RF-comtrade.csv'
# создаём директорию для данных, если она ещё не существует:
if (!file.exists('./data')) {
  dir.create('./data')
}
# загружаем файл, если он ещё не существует
if (!file.exists('./data/040510-Imp-RF-comtrade.csv')) {
  download.file(fileURL, './data/040510-Imp-RF-comtrade.csv')
}
# читаем данные из загруженного .csv во фрейм, если он ещё не существует
if (!exists('DT')){
  DT <- data.table(read.csv('./data/040510-Imp-RF-comtrade.csv', as.is = T))
}


#сохраняем в файл .png
png('Rplot.png', width = 700, height = 500)

# Построение коробчатой диаграммы массы поставки в килиграммах по переменной Netweight.kg (данные с пропусками)
boxplot(DT$Netweight.kg ~ DT$Year, main="Масса поставки",
        ylab = 'объем, в килограммах', xlab = 'год', col = 'yellow', border="red")



# переменные: масса поставки и её стоимость
x <- DT$Trade.Value.USD
y <- DT$Netweight.kg
# координаты пропущенных y по оси x
NAs <- x[is.na(y)]

# строим регрессию на логарифмах
fit.log <- lm(log(y) ~ log(x))
# новый столбец, в котором будут заполнены пропуски
DT[, Netweight.kg.model:=as.double(Netweight.kg)]
# прогноз по модели на логарифмах сохраняем как вектор
y.model.log <- predict(fit.log, newdata = data.frame(x = NAs))
# пересчитываем в исходные единицы измерения y
y.model <- exp(y.model.log)
y.model <- round(y.model, 0)
# заполняем пропуски модельными значениями
DT[is.na(Netweight.kg), Netweight.kg.model:=y.model]



# добавление коробчатой диаграммы массы поставки в килиграммах по переменной Netweight.kg.model 
# (данные с пропусками заполнены по модели регрессии)
boxplot(add = T, DT$Netweight.kg.model ~ DT$Year, main="Масса поставки",
        ylab = 'объем, в килограммах', xlab = 'год', col = 'green', border="blue")


# добавляем легенду на график
legend("top", c("по переменной Netweight.kg", "по переменной Netweight.kg.model"),
       fill = c("yellow", "green"))

# пропущенные значения (заменеы по модели регрессии) не являются аномальными (поэтому их не видно)

dev.off()







 