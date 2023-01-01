import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * A class Main which contains main basic operations and data input and output occurs.
 */
public class Main {
    private void main() { }
    public static void main(String[] args) {
        try {
            File input = new File("input.txt");
            Scanner scanner = new Scanner(input);
            int d = Integer.parseInt(scanner.nextLine());
            float g = Float.parseFloat(scanner.nextLine());
            final float minGrass = 0;
            final float maxGrass = 100;
            if ((g < minGrass) || (g > maxGrass)) {
                System.out.println(new GrassOutOfBoundsException().getMessage());
                System.exit(0);
            }
            int n = Integer.parseInt(scanner.nextLine());
            final int maxDays = 30;
            final int maxAnimals = 20;
            if ((d < 1) || (d > maxDays) || (n < 1) || (n > maxAnimals)) {
                System.out.println(new InvalidInputsException().getMessage());
                System.exit(0);
            }
            ArrayList<Animal> animals = new ArrayList<>();
            String inputStr;
            final int trueParameters = 4;
            final float minWeight = 5;
            final float maxWeight = 200;
            final float minSpeed = 5;
            final float maxSpeed = 60;
            final float minEnergy = 0;
            final float maxEnergy = 100;
            while (scanner.hasNextLine()) {
                inputStr = scanner.nextLine();
                String[] parameters = inputStr.split(" ");
                if (parameters.length != trueParameters) {
                    System.out.println(new InvalidNumberOfAnimalParametersException().getMessage());
                    System.exit(0);
                }
                String animal = parameters[0];
                if ((!(animal.equals("Lion"))) && (!(animal.equals("Zebra"))) && (!(animal.equals("Boar")))) {
                    System.out.println(new InvalidInputsException().getMessage());
                    System.exit(0);
                }
                float weight = Float.parseFloat(parameters[1]);
                if ((weight < minWeight) || (weight > maxWeight)) {
                    System.out.println(new WeightOutOfBoundsException().getMessage());
                    System.exit(0);
                }
                float speed = Float.parseFloat(parameters[2]);
                if ((speed < minSpeed) || (speed > maxSpeed)) {
                    System.out.println(new SpeedOutOfBoundsException().getMessage());
                    System.exit(0);
                }
                final int indexThree = 3;
                float energy = Float.parseFloat(parameters[indexThree]);
                if ((energy < minEnergy) || (energy > maxEnergy)) {
                    System.out.println(new EnergyOutOfBoundsException().getMessage());
                    System.exit(0);
                }
                switch (animal) {
                    case "Lion":
                        animals.add(new Lion(AnimalType.parse(animal), weight, speed, energy));
                        break;
                    case "Zebra":
                        animals.add(new Zebra(AnimalType.parse(animal), weight, speed, energy));
                        break;
                    case "Boar":
                        animals.add(new Boar(AnimalType.parse(animal), weight, speed, energy));
                        break;
                    default:
                        System.out.println(new InvalidInputsException().getMessage());
                        System.exit(0);
                }
            }
            runSimulation(d, g, animals);
            printAnimals(animals);
        } catch (Exception e) {
            System.out.println(new InvalidInputsException().getMessage());
            System.exit(0);
        }
    }
    public static void runSimulation(int d, float g, ArrayList<Animal> animals) {
        final double coef = 0.1;
        final float maxGrass = 100;
        final float maxEnergy = 100;
        for (int i = 0; i < d; i++) {
            removeDeadAnimals(animals);
            for (int j = 0; j < animals.size(); j++) {
                switch (animals.get(j).type) {
                    case Herbivore:
                        if (animals.get(j).energy > 0) {
                            if (g > animals.get(j).weight * coef) {
                                g -= animals.get(j).weight * coef;
                                animals.get(j).energy += animals.get(j).weight * coef;
                                if (animals.get(j).energy > maxEnergy) {
                                    animals.get(j).energy = maxEnergy;
                                }
                            }
                        }
                        break;
                    case Carnivore:
                        if (animals.get(j).energy > 0) {
                            if (j != (animals.size()) - 1) {
                                if (animals.get(j + 1).energy > 0) {
                                    if (animals.get(j).type == animals.get(j + 1).type) {
                                        System.out.println(new CannibalismException().getMessage());
                                    } else {
                                        if ((animals.get(j).speed > animals.get(j + 1).speed)
                                                || (animals.get(j).energy > animals.get(j + 1).energy)) {
                                            animals.get(j).energy += animals.get(j + 1).weight;
                                            animals.get(j + 1).energy = 0;
                                            if (animals.get(j).energy > maxEnergy) {
                                                animals.get(j).energy = maxEnergy;
                                            }
                                        } else {
                                            System.out.println(new TooStrongPreyException().getMessage());
                                        }
                                    }
                                }
                            } else {
                                if (j == 0) {
                                    System.out.println(new SelfHuntingException().getMessage());
                                } else {
                                    if (animals.get(0).energy > 0) {
                                        if (animals.get(j).type == animals.get(0).type) {
                                            System.out.println(new CannibalismException().getMessage());
                                        } else {
                                            if ((animals.get(j).speed > animals.get(0).speed)
                                                    || (animals.get(j).energy > animals.get(0).energy)) {
                                                animals.get(j).energy += animals.get(0).weight;
                                                animals.get(0).energy = 0;
                                                if (animals.get(j).energy > maxEnergy) {
                                                    animals.get(j).energy = maxEnergy;
                                                }
                                            } else {
                                                System.out.println(new TooStrongPreyException().getMessage());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        if (animals.get(j).energy > 0) {
                            if (g > animals.get(j).weight * coef) {
                                g -= animals.get(j).weight * coef;
                                animals.get(j).energy += animals.get(j).weight * coef;
                                if (animals.get(j).energy > maxEnergy) {
                                    animals.get(j).energy = maxEnergy;
                                }
                            }
                            if (j != (animals.size()) - 1) {
                                if (animals.get(j + 1).energy > 0) {
                                    if (animals.get(j).type == animals.get(j + 1).type) {
                                        System.out.println(new CannibalismException().getMessage());
                                    } else {
                                        if ((animals.get(j).speed > animals.get(j + 1).speed)
                                                || (animals.get(j).energy > animals.get(j + 1).energy)) {
                                            animals.get(j).energy += animals.get(j + 1).weight;
                                            animals.get(j + 1).energy = 0;
                                            if (animals.get(j).energy > maxEnergy) {
                                                animals.get(j).energy = maxEnergy;
                                            }
                                        } else {
                                            System.out.println(new TooStrongPreyException().getMessage());
                                        }
                                    }
                                }
                            } else {
                                if (j == 0) {
                                    System.out.println(new SelfHuntingException().getMessage());
                                } else {
                                    if (animals.get(0).energy > 0) {
                                        if (animals.get(j).type == animals.get(0).type) {
                                            System.out.println(new CannibalismException().getMessage());
                                        } else {
                                            if ((animals.get(j).speed > animals.get(0).speed)
                                                    || (animals.get(j).energy > animals.get(0).energy)) {
                                                animals.get(j).energy += animals.get(0).weight;
                                                animals.get(0).energy = 0;
                                                if (animals.get(j).energy > maxEnergy) {
                                                    animals.get(j).energy = maxEnergy;
                                                }
                                            } else {
                                                System.out.println(new TooStrongPreyException().getMessage());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
            }
            g *= 2;
            if (g > maxGrass) {
                g = maxGrass;
            }
            for (int j = 0; j < animals.size(); j++) {
                animals.get(j).energy -= 1;
            }
        }
    }
    public static void printAnimals(ArrayList<Animal> animals) {
        removeDeadAnimals(animals);
        for (Animal animal : animals) {
            System.out.println(new AnimalSound().getSound(animal.type));
        }
    }
    private static void removeDeadAnimals(ArrayList<Animal> animals) {
        int size = animals.size();
        int i = 0;
        float diedEnergy = 0;
        while ((i != size) && (size != 0)) {
            if (animals.get(i).energy <= diedEnergy) {
                animals.remove(i);
                size -= 1;
                i -= 1;
            }
            i += 1;
        }
    }
}
abstract class Animal {
    protected AnimalType type;
    protected float weight;
    protected float speed;
    protected float energy;
    protected Animal(AnimalType typeA, float weightA, float speedA, float energyA) {
        this.type = typeA;
        this.weight = weightA;
        this.speed = speedA;
        this.energy = energyA;
    }
}
class Lion extends Animal {
    Lion(AnimalType type, float weight, float speed, float energy) {
        super(type, weight, speed, energy);
    }
}
class Zebra extends Animal {
    Zebra(AnimalType type, float weight, float speed, float energy) {
        super(type, weight, speed, energy); }
}
class Boar extends Animal {
    Boar(AnimalType type, float weight, float speed, float energy) {
        super(type, weight, speed, energy);
    }
}
class AnimalSound {
    public String getSound(AnimalType type) {
        if (type == AnimalType.Herbivore) {
            return "Ihoho";
        } else {
            if (type == AnimalType.Carnivore) {
                return "Roar";
            } else {
                return "Oink";
            }
        }
    }
}
enum AnimalType {
    Herbivore,
    Carnivore,
    Omnivore;
    public static AnimalType parse(String animal) {
        if (animal.equals("Zebra")) {
            return Herbivore;
        } else {
            if (animal.equals("Lion")) {
                return Carnivore;
            } else {
                return Omnivore;
            }
        }
    }
}
class InvalidNumberOfAnimalParametersException {
    public String getMessage() {
        return "Invalid number of animal parameters";
    }
}
class InvalidInputsException {
    public String getMessage() {
        return "Invalid inputs";
    }
}
class GrassOutOfBoundsException {
    public String getMessage() {
        return "The grass is out of bounds";
    }
}
class CannibalismException {
    public String getMessage() {
        return "Cannibalism is not allowed";
    }
}
class TooStrongPreyException {
    public String getMessage() {
        return "The prey is too strong or too fast to attack";
    }
}
class SelfHuntingException {
    public String getMessage() {
        return "Self-hunting is not allowed";
    }
}
class SpeedOutOfBoundsException {
    public String getMessage() {
        return "The speed is out of bounds";
    }
}
class EnergyOutOfBoundsException {
    public String getMessage() {
        return "The energy is out of bounds";
    }
}
class WeightOutOfBoundsException {
    public String getMessage() {
        return "The weight is out of bounds";
    }
}
