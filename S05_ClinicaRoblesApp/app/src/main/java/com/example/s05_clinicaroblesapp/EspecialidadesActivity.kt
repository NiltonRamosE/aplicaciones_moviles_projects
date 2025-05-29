package com.example.s05_clinicaroblesapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView

class EspecialidadesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EspecialidadAdapter
    private lateinit var listaEspecialidades: List<Especialidad>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_especialidades)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_especialidades)
        setSupportActionBar(toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        recyclerView = findViewById(R.id.recyclerEspecialidades)

        listaEspecialidades = listOf(
            Especialidad("Pediatría", "Atención médica para niños y adolescentes", R.drawable.pediatria,
                listOf(
                    Doctor("Dr. Marcos Vásquez Tantas", "Lun-Vie 8:00 - 13:00"),
                    Doctor("Dr. Jaime Cabrera Pereda", "Lun-Sáb 9:00 - 14:00"),
                    Doctor("Dr. Hugo Melendez Cuentas", "Lun-Vie 8:30 - 12:30"),
                    Doctor("Dr. Juan Solis Anceles", "Mar-Vie 10:00 - 15:00"),
                    Doctor("Dra. Rosa Anceldonis Hurtado", "Mar-Vie 10:00 - 16:00"),
                    Doctor("Dr. Edward Ramírez Alvear", "Lun-Mié 14:00 - 18:00"),
                    Doctor("Dr. Fernando Samame", "Mié-Vie 13:00 - 17:00"),
                    Doctor("Dra. Ana Lopez", "Jue-Sáb 9:00 - 13:00"),
                    Doctor("Dr. Jose Lozano", "Lun-Vie 15:00 - 19:00"),
                )
            ),
            Especialidad("Cardiología", "Prevención y tratamiento de enfermedades del corazón", R.drawable.cardiologia,
                listOf(
                    Doctor("Dr. Roberto Chavesta Bernal", "Mar-Vie 13:00 - 17:00"),
                )
            ),
            Especialidad("Ginecología", "Salud femenina y control prenatal", R.drawable.ginecologia,
                listOf(
                    Doctor("Dr. Jose Luis Espinoza Decena", "Lun-Vie 9:00 - 13:00"),
                    Doctor("Dra. Yulissa Paredes Padilla", "Mar-Jue 14:00 - 18:00"),
                    Doctor("Dr. Julio Ostolaza Rodriguez", "Lun, Mié y Vie 10:00 - 15:00"),
                    Doctor("Dra. Leslie Sosa De La Cruz", "Mié-Sáb 8:30 - 12:30")
                )
            ),
            Especialidad("Medicina General", "Consultas generales y chequeos preventivos", R.drawable.general,
                listOf(
                    Doctor("Dr. Elwis Laveriano Hoyos", "Lun-Vie 8:00 - 12:00"),
                    Doctor("Dra. Maria Cristina Saavedra", "Lun-Sáb 14:00 - 18:00")
                )
            ),
            Especialidad("Traumatología", "Tratamiento de lesiones óseas y musculares", R.drawable.traumatologia,
                listOf(
                    Doctor("Dr. Alberto Garcia Cerna", "Mar-Vie 13:00 - 17:00"),
                    Doctor("Dr. Cruz Medina Martinez", "Lun-Sáb 10:00 - 14:00"),
                    Doctor("Dr. Edison Abanto Arroyo", "Jue-Sáb 10:00 - 13:00"),
                    Doctor("Dra. Sandra Robles Zanelli", "Lun-Vie 8:00 - 12:00"),
                    Doctor("Dra. Yessenia Zapata Delgado", "Mié-Vie 15:00 - 19:00"),
                    Doctor("Dr. Ciro Madrid Flores", "Mié-Vie 15:00 - 19:00"),
                    Doctor("Dr. Pablo Mego", "Jue-Sáb 10:00 - 13:00"),
                )
            ),
            Especialidad("Cirugía General", "Intervenciones quirúrgicas generales", R.drawable.cirugia_general,
                listOf(
                    Doctor("Dr. Beto Miranda Sevillano", "Lun-Mié-Vie 8:00 - 12:00"),
                    Doctor("Dr. Pool Jara Pesantes", "Mar-Jue 14:00 - 18:00"),
                    Doctor("Dr. Jonnatan Uribe", "Lun-Vie 9:00 - 13:00"),
                    Doctor("Dr. Beltrany Lavado", "Sáb 8:00 - 13:00")
                )
            ),
            Especialidad("Dermatología", "Tratamiento de enfermedades de la piel", R.drawable.dermatologia,
                listOf(
                    Doctor("Dr. Jaime Vega Chavez", "Lun, Mié, Vie 9:00 - 13:00"),
                    Doctor("Dr. Italo Vecas Jaramillo", "Mar, Jue 14:00 - 18:00")
                )
            ),
            Especialidad("Endocrinología", "Trastornos hormonales y metabólicos", R.drawable.endocrinologia,
                listOf(
                    Doctor("Dr. Juan Pinto Sanchez", "Lun a Vie 8:00 - 14:00")
                )
            ),
            Especialidad("Gastroenterología", "Enfermedades del sistema digestivo", R.drawable.gastroenterologia,
                listOf(
                    Doctor("Dra. Kelly Casanova Lau", "Mar, Jue, Vie 9:00 - 15:00")
                )
            ),
            Especialidad("Medicina Interna", "Diagnóstico integral de enfermedades complejas", R.drawable.medicina_interna,
                listOf(
                    Doctor("Dr. Luis Cabrera Cipiran", "Lun, Mié, Vie 8:00 - 14:00"),
                    Doctor("Dra. Rosa Casimiro Lau", "Mar, Jue 10:00 - 16:00"),
                    Doctor("Dra. Yuriko Zuñica Lavado", "Lun, Mar, Vie 9:00 - 13:00")
                )
            ),
            Especialidad("Neumología", "Diagnóstico y tratamiento de enfermedades del aparato respiratorio", R.drawable.neumologia,
                listOf(
                    Doctor("Dra. Yessica Montoya Caldas", "Lun, Mié, Vie 8:00 - 13:00"),
                    Doctor("Dr. Walter Centurion Jaramillo", "Mar, Jue 10:00 - 15:00")
                )
            ),
            Especialidad("Neurología", "Trastornos del sistema nervioso", R.drawable.neurologia,
                listOf(
                    Doctor("Dra. Rosnel Valdivieso Velarde", "Lun, Mié, Vie 8:00 - 13:00"),
                )
            ),
            Especialidad("Nutrición", "Asesoramiento sobre alimentación saludable", R.drawable.nutricion,
                listOf(
                    Doctor("Lic. Carmela Carbajal", "Lun a Vie 9:00 - 14:00")
                )
            ),
            Especialidad("Odontología", "Cuidado dental y salud bucal", R.drawable.odontologia,
                listOf(
                    Doctor("Dr. Pedro Cipriano Alegre", "Lun, Mié y Vie 8:00 - 13:00"),
                    Doctor("Dra. Carolina Acuña Calderón", "Mar y Jue 10:00 - 15:00")
                )
            ),
            Especialidad("Oftalmología", "Salud visual y tratamiento ocular", R.drawable.oftalmologia,
                listOf(
                    Doctor("Dra. Anita Vigo Arroyo", "Lun a Vie 9:00 - 14:00")
                )
            ),
            Especialidad("Psicología", "Atención emocional y salud mental", R.drawable.psicologia,
                listOf(
                    Doctor("Lic. Astrid Manrique Marron", "Lun, Mié, Vie 10:00 - 15:00"),
                    Doctor("Lic. Luz Vasquez Burcos", "Mar, Jue 9:00 - 14:00")
                )
            ),
            Especialidad("Psiquiatría", "Tratamiento médico de trastornos mentales", R.drawable.psiquiatria,
                listOf(
                    Doctor("Dra. Celmira Lazaro Loyola", "Lun a Vie 8:00 - 12:00")
                )
            ),
            Especialidad("Urología", "Sistema urinario y salud reproductiva masculina", R.drawable.urologia,
                listOf(
                    Doctor("Dr. Carlos Morales Flores", "Lun, Mié, Vie 9:00 - 13:00"),
                    Doctor("Dr. Luis Pascual Plasencia", "Mar, Jue 10:00 - 14:00"),
                    Doctor("Dr. Jose Acosta Fuentes", "Lun a Viernes 8:00 - 12:00")
                )
            ),
            Especialidad("Cirugía Cardiovascular", "Intervenciones quirúrgicas en el sistema cardiovascular", R.drawable.cirugia_cardiovascular,
                listOf(
                    Doctor("Dr. Romel Zamudio Silva", "Lun a Viernes 8:00 - 14:00")
                )
            ),
            Especialidad("Medicina Física", "Rehabilitación y terapias físicas", R.drawable.medicina_fisica,
                listOf(
                    Doctor("Dr. Luis Vasquez", "Mar, Jue 9:00 - 13:00")
                )
            ),
            Especialidad("Neurocirugía", "Cirugía del sistema nervioso", R.drawable.neurocirugia,
                listOf(
                    Doctor("Dr. Willy Caballero Crados", "Lun, Mié, Vie 10:00 - 15:00")
                )
            ),
            Especialidad("Cirugía Maxilofacial", "Cirugía facial y mandibular", R.drawable.cirugia_maxilofacial,
                listOf(
                    Doctor("Dr. Julio Robles Zanelli", "Lun a Viernes 8:00 - 12:00")
                )
            ),
            Especialidad("Otorrino", "Salud auditiva y del oído, nariz y garganta", R.drawable.otorrino,
                listOf(
                    Doctor("Dr. Jorge Bonilla Vargas", "Lun, Mié, Vie 9:00 - 13:00")
                )
            ),
            Especialidad("Reumatología", "Enfermedades reumáticas y autoinmunes", R.drawable.reumatologia,
                listOf(
                    Doctor("Dr. Frank Ocaña Vasquez", "Mar, Jue 10:00 - 14:00")
                )
            )
        )

        adapter = EspecialidadAdapter(listaEspecialidades)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search_especialidad)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.queryHint = "Buscar especialidad..."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SEARCH", "Texto buscado: $newText")
                adapter.filter(newText ?: "")
                return true
            }
        })

        return true
    }
}