package org.example

// 1. กำหนด data class สำหรับเก็บข้อมูลสินค้า
data class Product(val name: String, val price: Double, val category: String)

fun main() {
    // 2. สร้างรายการสินค้าตัวอย่าง (List<Product>)
    val products = listOf(
        Product("Laptop",     35_000.0, "Electronics"),
        Product("Smartphone", 25_000.0, "Electronics"),
        Product("T-shirt",       450.0, "Apparel"),
        Product("Monitor",     7_500.0, "Electronics"),
        Product("Keyboard",      499.0, "Electronics"),   // ราคา ≤ 500
        Product("Jeans",       1_200.0, "Apparel"),
        Product("Headphones",  1_800.0, "Electronics")    // ตรงตามเงื่อนไข
    )

    println("รายการสินค้าทั้งหมด:")
    products.forEach { println(it) }
    println("--------------------------------------------------")

    // --- โจทย์: หาผลรวมราคาสินค้าในหมวด 'Electronics' ที่มีราคามากกว่า 500 บาท ---

    // 3. วิธีที่ 1: ใช้ Chaining กับ List โดยตรง
    val totalElecPriceOver500 = products
        .filter { it.category == "Electronics" }   // กรองหมวด
        .filter { it.price > 500 }                 // กรองราคา
        .sumOf { it.price }                        // หาผลรวม

    println("วิธีที่ 1: ใช้ Chaining กับ List")
    println("ผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท: $totalElecPriceOver500 บาท")
    println("--------------------------------------------------")

    // 4. วิธีที่ 2: ใช้ .asSequence() เพื่อเพิ่มประสิทธิภาพ
    val totalElecPriceOver500Sequence = products
        .asSequence()                              // แปลงเป็น Sequence
        .filter { it.category == "Electronics" }
        .filter { it.price > 500 }
        .sumOf { it.price }                        // Terminal operation

    println("วิธีที่ 2: ใช้ .asSequence() (ขั้นสูง)")
    println("ผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท: $totalElecPriceOver500Sequence บาท")
    println("--------------------------------------------------")

    val groupedByPrice: Map<String, List<Product>> = products.groupBy { p ->
        when {
            p.price <= 1_000      -> "ไม่เกิน1,000 บาท"
            p.price < 10_000      -> "1,001 – 9,999 บาท"
            else                  -> "10,000 บาทึ้นไป"
        }
    }

    println("แบ่งกลุ่มสินค้าตามช่วงราคา:")
    groupedByPrice.forEach { (range, items) ->
        println(">> กลุ่ม $range : ")
        items.forEach { println("   • ${it.name}  ราคา ${"%,.0f".format(it.price)} บาท") }
    }
}
