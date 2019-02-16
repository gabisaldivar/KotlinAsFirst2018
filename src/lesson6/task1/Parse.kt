@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.lang.IllegalArgumentException


/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    if (str.matches(Regex("""[0-9]+\s[а-яА-Я]+\s[0-9]+"""))) {
        val map = mapOf("января" to 1, "февраля" to 2, "марта" to 3, "апреля" to 4, "мая" to 5,
                "июня" to 6, "июля" to 7, "августа" to 8, "сентября" to 9, "октября" to 10, "ноября" to 11,
                "декабря" to 12)
        val data = str.split(" ")
        if (data.size != 3) return " "
        val day = data[0].toInt()
        val month = map[data[1]]
        val year = data[2].toInt()
        var day2 = 0
        if (((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) && (month == 2)) {
            day2 += 29
        } else if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) ||
                (month == 10) || (month == 12)) {
            day2 += 31
        } else if (month == 2) {
            day2 += 28
        } else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            day2 += 30
        }
        if (day2 >= day) {
            return String.format("%02d.%02d.%d", day, month, year)
        }
    }
    return ""
}
// done
/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    if (digital.matches(Regex("""[0-9]+.[0-9]+.[0-9]+"""))) {
        val list = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа",
                "сентября", "октября", "ноября", "декабря")
        val date = digital.split(".")
        if (date.size != 3) return " "
        val day = date[0].toInt()
        val year = date[2].toInt()
        val month = date[1].toInt()
        var day2 = 0
        if (((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) && (month == 2)) {
            day2 += 29
        } else if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) ||
                (month == 10) || (month == 12)) {
            day2 += 31
        } else if (month == 2) {
            day2 += 28
        } else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            day2 += 30
        }
        return if (day2 >= day) {
            String.format("%d %s %d", day, list[month - 1], year)
        } else ""

    } else return ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val list = phone.split(" ", "-", "(", ")")
    val number = StringBuilder()
    for (i in list) {
        if (i != "")
            number.append(i)
    }
    if ((number.matches(Regex("""[+]?[0-9]+""")))) {
        return number.toString()
    }
    return ""
}


/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val distanceOfjumps = jumps.split(" ", "-", "%")
    var numbers = -1
    return try {
        for (i in distanceOfjumps) {
            if (i != "") {
                if (i.toInt() > numbers) {
                    numbers = i.toInt()
                }
            }
        }
        numbers
    } catch (e: NumberFormatException) {
        -1
    }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val jumpS = jumps.split(" ", "-", "%")
    var number = -1
    return try {
        val x = jumpS.filter { it != "" }
        for (i in 0 until x.size) {
            if ((x[i] == "+") && (x[i - 1].toInt() > number)) {
                number = x[i - 1].toInt()
            }
        }
        number
    } catch (e: NumberFormatException) {
        -1
    }
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (!expression.matches(Regex("""^[0-9]+( [-+] [0-9]+)*""")))
        throw IllegalArgumentException()
    var result: Int
    val operation = expression.split(" ")
    if (operation.size == 1 && !(operation[0]).contains("+") && !(operation[0]).contains("-")) {
        return operation[0].toInt()
    } else {
        result = operation[0].toInt()
        for (element in 2..operation.size step (2)) {
            if (operation[element - 1] == "+") {
                result += operation[element].toInt()
            } else if (operation[element - 1] == "-") {
                result -= operation[element].toInt()
            }
        }
    }

    return result

}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int = TODO()

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (!description.matches(Regex("""[а-яА-Я]+ \d.+(; [а-яА-Я]+ \d+)*""")))
        return "Any good with price 2.147483647E7"
    val y = description.split(" ")
    if (y.size == 2 && y[1].toDouble() > 0.0) return y[0]
    val productAndprice = description.split(";").map { it.trim() }
    var temporalPrice = 0.0
    var result = " "
    for (element in productAndprice) {
        val x = element.split(" ")
        val price = x[1].toDouble()
        if (price > temporalPrice) {
            result = x[0]
            temporalPrice = price
        }
    }
    return result
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 * "MCMLXXVIII
 * "
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val mapa = mapOf("I" to 1, "V" to 5, "X" to 10, "L" to 50, "C" to 100, "D" to 500, "M" to 1000)
    val romanNumber = roman.split("").filter { it != "" }.reversed()
    if (!romanNumber.all { eleCh -> "IVXLCDM".contains(eleCh) } || romanNumber.isEmpty())
        return -1
    var value: Int
    var nextValue: Int
    var result = mapa[romanNumber[0]]!!
    for (i in 0 until romanNumber.size) {
        if (i == romanNumber.size - 1) break
        else {
            value = mapa[romanNumber[i]]!!
            nextValue = mapa[romanNumber[i + 1]]!!
            println(nextValue)
            if (value <= nextValue) {
                result += nextValue

            } else result -= nextValue

        }
    }
    return result
}


/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()

