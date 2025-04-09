public class Pet {
    private String aniD;
    private String name;
    private String breed;
    private int age;
    private boolean adopted;

    public Pet(String aniD, String name, String breed, int age) {
        this.aniD = aniD;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.adopted = false; // Default value for adopted
    }

    public String getName(){
        return name;
    }

    public String getBreed(){
        return breed;
    }

    public String getSpecies(){

        switch (aniD.split("_")[0]) {
            case "D":
                return "Dog";
            case "C":
                return "Cat";
            case "B":
                return "Bird";
            case "R":
                return "Reptile";
            case "F":
                return "Fish";
            default:
                return "Unknown";
        }
        

    }

    public String getId(){
        return aniD;
    }

    public int getAge(){
        return age;
    }

    public String getName(){
    return name;
    }

    public String getBreed() { 
        return breed;
 }

    public boolean getAdoptionStat(){
        return adopted;
    }

    public void setAdoptionStat(boolean adopted) {
        this.adopted = adopted;
    }

    public String toString() {
        return "Pet ID: " + aniD + ", Name: " + name + ", Breed: " + breed + ", Age: " + age + ", Adopted: " + adopted;
    }
}