# Functional Programming in Scala 3 with Cats and Cats Effect

Functional Programming in Scala 3
with Cats and Cats Effect
A 12-week professional course for experienced Scala developers

Production-oriented • Balanced theory and hands-on practice • Scala 3

Course detail                      Specification

Duration                           12 weeks

Guided learning                    48 hours (4 hours per week)

Independent work                   Approximately 3–5 hours per week

Audience                           Developers who already know and use Scala

Primary outcome                    Design, implement, test and operate reliable functional applications

Course purpose: move experienced Scala developers from using functional idioms informally to applying
functional programming principles deliberately in production systems.

Course syllabus and instructor planning guide

# 1. Course specification
This course assumes learners already understand Scala 3 syntax, case classes, enums, pattern matching, collections,
generics, Future and asynchronous application development. It does not teach Scala from scratch.

### Learning outcomes
- Explain referential transparency, effect descriptions, algebraic laws and the relationship between pure and effectful code.

- Use Cats abstractions to compose computations without unnecessary boilerplate or abstraction.

- Model business validation, domain errors and application failures explicitly.

- Design IO-based programs with correct sequencing, error handling, cancellation and resource lifecycles.

- Use Cats Effect concurrency primitives and FS2 streams to build controlled, resilient asynchronous workflows.

- Design functional application boundaries using effect polymorphism where it adds value.

- Test pure logic, effects, concurrency and integration behaviour deterministically.

- Build and justify a production-oriented functional Scala application.

### Curriculum at a glance
Week        Theme                                    Main topics

1           Functional design and purity             Pure functions, effects, totality

2           Type classes and algebra                 Higher-kinded types, laws, Semigroup, Monoid

3           Functor and Applicative                  Mapping, independent computations, validation

4           Monad and error composition              flatMap, Either, MonadError, transformers

5           Foldable and Traverse                    Collection processing and effect composition

6           Cats Effect and IO                       Execution model, errors, runtime

7           Resource safety and architecture         Resource, capabilities, dependency composition

8           Concurrency and cancellation             Fibers, parallelism, coordination

9           Resilience and time                      Retries, timeouts, idempotency, observability

10          FS2 and streaming                        Backpressure, batching, resource safety

11          Testing and persistence                  Deterministic tests, transactions, integration

12          Capstone and design review               End-to-end implementation and assessment

## Week 1 — Functional design and referential
transparency
<b>Central question:</b> What makes a program functional, and why does that matter in production?

### Learning objectives
- Distinguish pure computations from effectful operations.

- Explain referential transparency and equational reasoning.

- Identify partial functions, hidden dependencies and uncontrolled side effects.

- Separate domain decisions from infrastructure concerns.

- Use immutable data and algebraic data types to make invalid states harder to represent.

### Lesson plan
Time                  Topic

60 min                Purity, referential transparency and substitution

45 min                Total functions, ADTs and explicit error modelling

45 min                Separating domain logic from side effects

90 min                Guided refactoring lab

### Teaching example
Begin with an imperative operation that mixes business rules, database access and logging:
def approveStaff(pid: String): Boolean = val staff = database.find(pid) if staff != null && staff.active
then database.markApproved(pid) logger.info(s"Approved $pid") true else false

Refactor the business decision into a pure function:
enum ApprovalDecision: case Approved case AlreadyApproved case Inactive case NotFound def
approvalDecision( staff: Option[Staff] ): ApprovalDecision = staff match case None =>
ApprovalDecision.NotFound case Some(s) if !s.active => ApprovalDecision.Inactive case Some(s) if
s.approved => ApprovalDecision.AlreadyApproved case Some(_) => ApprovalDecision.Approved

### Practical exercises
- Refactor three imperative business functions into pure functions.

- Replace nullable fields and Boolean result codes with meaningful ADTs.

- Identify side effects and hidden dependencies in a supplied service.

- Write table-driven tests for all approval decisions.
Assessment — Deliverable: a pure domain module with tests. Success criteria: business logic is
deterministic, explicit about outcomes, and independently testable without a database or mocks.

## Week 2 — Type classes, higher-kinded types and
algebraic laws
<b>Central question:</b> How can we abstract over behaviour without building complicated inheritance
hierarchies?

### Learning objectives
- Explain F[_] and why higher-kinded abstractions matter.

- Distinguish a type class, its instance and its syntax.

- Use Scala 3 given and using.

- Understand Eq, Show, Semigroup and Monoid.

- Explain associativity and identity laws.

- Recognise when an abstraction is useful and when a concrete function is better.

### Lesson plan
Time                  Topic

45 min                Type constructors, generic types and F[_]

60 min                Type classes, instances, syntax and Scala 3 givens

45 min                Semigroups, monoids and algebraic laws

90 min                Implementation and property-testing lab

### Practical exercises
- Define a given Eq[Staff] using Cats.

- Use Show for diagnostic output without coupling domain types to a logger.

- Implement a custom Semigroup for an application-specific aggregation type.

- Write property-based tests for associativity and identity.

- Compare a type-class-based solution with a generic function and justify the choice.
Assessment — Deliverable: a small algebraic abstraction with lawful instances and tests. Success criteria:
explain the laws, test the implementation and use Scala 3 contextual abstractions correctly.

## Week 3 — Functor, Applicative and validation
<b>Central question:</b> How do we compose computations when we do not want to unwrap every container
manually?

### Learning objectives
- Use map and Functor to transform values in a context.

- Explain the difference between mapping and sequencing.

- Use Applicative and mapN for independent computations.

- Distinguish fail-fast validation from error accumulation.

- Apply Validated, NonEmptyList and NonEmptyChain.

- Understand the relationship between Either and Validated.

### Lesson plan
Time                  Topic

45 min                Functor, map and functor laws

60 min                Applicative composition and mapN

45 min                Either versus Validated

90 min                Validation pipeline lab

### Practical exercises
- Implement a staff registration validator for personnel ID, name, email and team membership.

- Return all independent validation errors instead of forcing users to fix one at a time.

- Combine independent validation results with mapN.

- Explain why dependent checks may require sequential composition rather than Applicative validation.

Example
import cats.data.ValidatedNec import cats.syntax.all.* type Errors = String type Validation[A] =
ValidatedNec[Errors, A] def validateName(name: String): Validation[String] = if name.trim.nonEmpty then
name.validNec else "Name is required".invalidNec

Assessment — Deliverable: a compositional validation module. Success criteria: independent validation
failures accumulate correctly, while dependent business operations use appropriate sequencing.

## Week 4 — Monad, error handling and transformer
fundamentals
<b>Central question:</b> How should we compose computations when later operations depend on earlier
results?

### Learning objectives
- Understand flatMap, pure and monadic composition.

- Relate Scala for-comprehensions to map, flatMap and withFilter.

- Explain Monad and its laws.

- Model domain failures with Either.

- Understand MonadError and distinguish domain errors from exceptions.

- Recognise nested effects and when EitherT or OptionT can simplify them.

### Lesson plan
Time                  Topic

60 min                Monad and dependent sequencing

45 min                For-comprehensions and desugaring

45 min                Error models and recovery

90 min                Business workflow lab

### Practical exercises
- Implement an approval workflow: find staff, check activity, authorisation and prior approval, persist the result, and return an
explicit outcome.

- Model expected business failures separately from unexpected infrastructure errors.

- Compare Either[DomainError, Staff] with EitherT[IO, DomainError, Staff].

- Explain transformer ordering and avoid introducing a transformer stack without a concrete need.
Assessment — Deliverable: a compositional workflow with explicit error semantics. Success criteria: correct
sequencing, no deeply nested matches, and a defensible distinction between business errors and technical
failures.

## Week 5 — Foldable, Traverse and effect composition
<b>Central question:</b> How do we process collections when each element has a possible failure or effect?

### Learning objectives
- Use Foldable, foldMap and monoidal aggregation.

- Understand Traverse and sequence.

- Explain the type transformation from List[F[A]] to F[List[A]].

- Choose between fail-fast processing and accumulating validation.

- Distinguish sequential traversal from parallel traversal.

- Recognise that traversal order and error behaviour depend on the effect and instance.

### Lesson plan
Time                  Topic

45 min                Folding, aggregation and monoids

60 min                Traverse and sequence

45 min                Effect ordering and failure semantics

90 min                Batch-processing lab

### Practical exercises
- Parse a collection of input records.

- Validate records and accumulate errors where appropriate.

- Perform dependent database lookups.

- Return successful results or a clearly defined failure.

- Compare sequential processing with a bounded-parallel alternative.

- Explain the different contracts expressed by List[Either[ImportError, Staff]], Either[ImportError, List[Staff]] and
ValidatedNec[ImportError, List[Staff]].
Assessment — Deliverable: a batch-processing module with documented failure behaviour. Success criteria:
correct sequencing, deliberate error accumulation and no accidental semantic changes during refactoring.

## Week 6 — Cats Effect: IO and the execution model
<b>Central question:</b> How do we represent effects as values and control when they execute?

### Learning objectives
- Explain the distinction between IO[A] and A.

- Construct effect descriptions safely.

- Understand IO.pure, IO.delay, IO.blocking, IO.defer and IO.raiseError.

- Compose effects using map, flatMap, *>, as and attempt.

- Distinguish error values from failed effects.

- Understand IOApp and why unsafe execution belongs at application boundaries.

- Identify common mistakes involving eager evaluation and repeated execution.

### Lesson plan
Time                  Topic

45 min                Effects as values and delayed execution

60 min                Constructing and composing IO

45 min                Error handling and runtime execution

90 min                Refactoring a service from Future or synchronous I/O

### Practical exercises
- Convert an imperative service into an IO-based implementation.

- Demonstrate the difference between evaluating a value before constructing an IO and delaying the evaluation.

- Implement a service that reads configuration, calls a repository and logs the outcome.

- Identify unsafe execution boundaries and remove them from internal business code.

- Demonstrate that composing an IO does not, by itself, execute the described operation.

Example
import cats.effect.IO def readStaff(pid: String): IO[Staff] = IO.println(s"Looking up $pid") *>
repository.find(pid)

Assessment — Deliverable: an IO-based application service with a runnable entry point. Success criteria:
correct effect construction, sequencing, error propagation and runtime execution.

## Week 7 — Resource safety, effect polymorphism and
dependency composition
<b>Central question:</b> How do we manage application dependencies safely and decide when to abstract
over the effect type?

### Learning objectives
- Use Resource to manage acquisition and release.

- Understand cleanup on success, failure and cancellation.

- Model database connections, clients and background workers as managed resources.

- Understand Sync, Async, Temporal, Concurrent and MonadCancel at a practical level.

- Distinguish concrete IO-based code from effect-polymorphic code.

- Build dependencies explicitly rather than relying on global mutable state.

- Recognise when tagless-final architecture adds useful flexibility.

### Lesson plan
Time                  Topic

60 min                Resource and lifecycle management

45 min                Effect capabilities and F[_]

45 min                Dependency composition and application boundaries

90 min                Resource-managed service lab

### Practical exercises
- Build a database transactor as a managed resource.

- Compose a repository, service and HTTP client.

- Demonstrate resource finalisation after a failed operation.

- Compare a concrete StaffService using IO with an effect-polymorphic StaffService[F].

- Decide which abstraction is appropriate for a small application and which is appropriate for a reusable library.
trait StaffService: def find(pid: String): IO[Option[Staff]] trait StaffServiceF[F[_]]: def find(pid:
String): F[Option[Staff]]

Assessment — Deliverable: a resource-safe application composition with explicit dependencies. Success
criteria: no leaked resources, correct shutdown and a justified use of effect polymorphism.

## Week 8 — Concurrency, fibers and coordination
<b>Central question:</b> How can we run work concurrently without losing control over its lifetime?

### Learning objectives
- Distinguish concurrency from parallel execution.

- Create and manage fibers using start, join and cancel.

- Understand cancellation and finalisation.

- Use parMapN, parTraverse and Parallel.

- Coordinate work using Ref, Deferred, Semaphore and Queue.

- Bound concurrency to protect external systems.

- Avoid detached background work and unsafe shared mutable state.

### Lesson plan
Time                  Topic

45 min                Fibers and structured concurrency

60 min                Parallel composition and cancellation

45 min                Coordination primitives

90 min                Concurrent worker lab

### Practical exercises
- Build a worker that processes incoming staff updates from a queue.

- Process multiple updates concurrently while limiting simultaneous operations.

- Stop cleanly when cancellation is requested.

- Ensure resource cleanup occurs.

- Handle a failing item without unintentionally losing unrelated work.

- Compare sequential composition, parallel composition and explicitly managed fibers.
Assessment — Deliverable: a bounded-concurrency worker with tests. Success criteria: correct cancellation,
bounded parallelism, no uncontrolled background fibers and a documented failure policy.

## Week 9 — Resilience, timeouts, retries and observability
<b>Central question:</b> How do we make effectful applications behave predictably when dependencies fail
or become slow?

### Learning objectives
- Design retry policies with bounded attempts and backoff.

- Use timeouts and distinguish timeout from successful completion.

- Understand cancellation propagation.

- Explain why retries can duplicate side effects.

- Design idempotent operations and safe retry boundaries.

- Handle partial failures in concurrent workflows.

- Integrate structured logging, metrics and tracing without coupling domain logic to observability infrastructure.

### Lesson plan
Time                  Topic

45 min                Error classification and recovery

60 min                Timeouts, retries and idempotency

45 min                Logging and operational observability

90 min                Fault-injection lab

### Practical exercises
- Simulate an unreliable external service and implement a timeout.

- Add bounded retries and exponential backoff with jitter.

- Classify errors so validation failures are not retried.

- Design an idempotency strategy for a side-effecting operation.

- Add structured logs with correlation identifiers.

- Discuss at-most-once, at-least-once and effectively-once processing.

Key teaching point: a timeout does not prove the remote operation did not happen.
Assessment — Deliverable: a resilient integration with tests for failure and recovery. Success criteria:
bounded retries, safe cancellation, explicit error classification and no unjustified retries of non-idempotent
operations.

## Week 10 — FS2 and streaming data pipelines
<b>Central question:</b> How do we process large or continuous datasets without loading everything into
memory?

### Learning objectives
- Understand Stream[F, A] and stream compilation.

- Compose stream transformations using map, evalMap, filter and compile.

- Understand backpressure and bounded buffering.

- Manage file, network and database resources safely.

- Process records in batches.

- Apply concurrency controls to stream processing.

- Understand how errors and cancellation affect a stream.

### Lesson plan
Time                  Topic

45 min                Stream fundamentals and compilation

60 min                Effectful transformations and batching

45 min                Backpressure, buffering and concurrency

90 min                Streaming import lab

### Practical exercises
- Read a large file or stream of records.

- Parse and validate records incrementally.

- Group valid records into database batches.

- Record rejected rows separately.

- Limit concurrent processing.

- Clean up resources when the stream fails or is cancelled.

- Compare a List-based implementation with streaming and explain memory and operational trade-offs.
Assessment — Deliverable: a resource-safe, bounded-memory import pipeline. Success criteria: correct
batching, controlled resource usage, explicit error handling and no unnecessary materialisation of the entire
input.

## Week 11 — Testing, database integration and
transactions
<b>Central question:</b> How do we verify that a functional application behaves correctly, including its
effects and failure modes?

### Learning objectives
- Test pure functions with ordinary unit tests.

- Test IO programs without unsafe execution scattered through application code.

- Use property-based testing and Cats law testing where appropriate.

- Test resource finalisation, cancellation and error recovery.

- Understand deterministic time and concurrency testing.

- Integrate Cats Effect with database libraries such as Doobie.

- Test transaction boundaries and persistence behaviour using a real test database.

- Distinguish unit, integration and end-to-end tests.

### Lesson plan
Time                  Topic

45 min                Testing pure functions and algebraic laws

60 min                Testing effects, errors and cancellation

45 min                Database integration and transactions

90 min                Testing and reliability lab

### Practical exercises
- Write property-based tests for domain invariants.

- Test that resource finalisers run on both success and failure.

- Test retry behaviour using controlled dependencies.

- Verify transactional rollback after a failure.

- Test repository and service behaviour against a real database.

- Remove timing-dependent assertions from a flaky concurrent test.
Assessment — Deliverable: a repeatable automated test suite covering domain logic, effect behaviour and
database integration. Success criteria: deterministic tests where possible, meaningful failure-path coverage,
verified transaction semantics and no unnecessary reliance on mocks.

## Week 12 — Capstone implementation and design review
<b>Central question:</b> Can the learner apply the abstractions appropriately in a complete application?

The capstone should be developed incrementally throughout the preceding weeks. Week 12 is for final integration, testing,
review and assessment rather than starting a new project from scratch.

### Learning objectives
- Integrate pure domain logic with effectful infrastructure.

- Justify the choice of Cats abstractions.

- Build safe resource and dependency lifecycles.

- Implement concurrency or streaming where the problem benefits from it.

- Verify failure behaviour through automated tests.

- Explain architectural trade-offs and operational limitations.

Practical requirements
- A well-defined domain model and pure business rules.

- Explicit validation and error handling.

- An IO-based application entry point.

- Managed resources using Resource.

- Persistent storage and transaction boundaries.

- At least one meaningful concurrent or streaming workflow.

- Structured logging and a documented failure policy.

- Unit, property and integration tests.

- A README describing how to run and test the application.

### Final assessment
Each learner submits the source code, automated tests and a short design document. The design review should cover:

- Why was Either used here and IO there?

- Where is validation accumulated, and where is failure short-circuited?

- Which resources are acquired and released, and who owns their lifecycle?

- What happens when cancellation occurs during persistence?

- Which operations may be retried safely?

- Where is concurrency bounded?

- Which abstractions could be removed without making the design worse?
Success criteria: the application works, the failure semantics are intentional, and the learner can defend the
design rather than simply reproduce library syntax.

# 4. Capstone project specification
Recommended project: a Staff and Team Management Service with an asynchronous import pipeline. It is realistic enough
to teach production concerns without allowing framework configuration to dominate.

### Reference architecture
<b>HTTP API / Import Source</b><br/>Requests, batch files and incoming updates

↓

<b>Application services</b><br/>IO, validation, orchestration and domain rules

↓

<b>Persistence</b><br/>Doobie, SQL and transactions                  <b>Background work</b><br/>Cats Effect fibers and FS2

<b>Cross-cutting concerns</b><br/>Resource safety · error handling · observability · cancellation · automated testing

### Suggested repository structure
src/main/scala/ domain/ Staff.scala Team.scala Validation.scala application/ StaffService.scala
StaffImportService.scala infrastructure/ StaffRepository.scala Database.scala Logging.scala api/
StaffController.scala Main.scala src/test/scala/ domain/ application/ infrastructure/

### Project milestones
Week                Milestone

1–2                 Domain model, pure rules and algebraic abstractions

3–5                 Validation, business workflows and batch-processing semantics

6–7                 IO, managed resources and application composition

8–9                 Concurrent processing, cancellation and resilience

10                  FS2 import pipeline

11                  Integration tests and transactional guarantees

12                  Final review and demonstration

This is a teaching structure, not a mandatory architecture. Students may choose a different package organisation if they can
justify it.

# 5. Assessment strategy
Reward correct reasoning and reliable software design, not merely the ability to use Cats syntax.

Assessment component                      Weight           Evidence

Weekly practical exercises                25%              Short implementations, refactoring and explanations

Focused technical assessments             20%              Type classes, effect semantics, errors and concurrency

Capstone implementation                   40%              Working application, architecture, tests and operational behaviour

Design review                             15%              Trade-offs, laws, failure semantics and abstraction choices

### Capstone marking rubric
Criterion                                                                                   Weight

Functional design and domain modelling                                                      20%

Correct use of Cats abstractions                                                            15%

Cats Effect semantics and resource safety                                                   20%

Concurrency, resilience and streaming                                                       15%

Automated testing and correctness                                                           20%

Documentation and design justification                                                      10%

Total                                                                                       100%

Important: compiling code is not evidence that an effectful program is correct. Assess failure, cancellation,
repeated execution, concurrent updates and resource release.

# 6. Recommended development environment
Use a single, pinned and compatible set of library versions for the whole cohort. Verify exact versions when preparing the
course repository rather than mixing examples from different major releases of Cats Effect.

Component                                Recommendation

Language                                 Scala 3

Build                                    sbt

Functional abstractions                  Cats Core

Effect runtime                           Cats Effect 3

Streaming                                FS2

Database integration                     Doobie and a relational database

Unit testing                             ScalaTest or MUnit

Property-based testing                   ScalaCheck

Law testing                              Discipline with Cats laws

Formatting and linting                   Scalafmt and suitable Scala 3 linting rules

### Primary documentation
<link href="https://typelevel.org/cats/" color="#2F65A7">Cats documentation</link> — https://typelevel.org/cats/

<link href="https://typelevel.org/cats-effect/" color="#2F65A7">Cats Effect documentation</link> —
https://typelevel.org/cats-effect/

<link href="https://fs2.io/" color="#2F65A7">FS2 documentation</link> — https://fs2.io/

<link href="https://typelevel.org/doobie/" color="#2F65A7">Doobie documentation</link> — https://typelevel.org/doobie/

<link href="https://scalacheck.org/" color="#2F65A7">ScalaCheck</link> — https://scalacheck.org/

Provide a starter repository with the build, test framework, database configuration and CI pipeline already working. Learners
should spend their time on functional design, not debugging build configuration.

# 7. Instructor preparation and course materials
- Twelve lesson guides, each containing objectives, explanatory diagrams, examples and common misconceptions.

- Twelve exercise sets, with starter code and separate reference solutions.

- A continuously evolving reference application tagged at each weekly milestone.

- A test suite demonstrating expected behaviour and common failure modes.

- Assessment rubrics for technical exercises and the capstone.

- An instructor guide with discussion prompts, extension exercises and alternative solutions.

Maintain two versions of the code for each week: a student version with clearly defined TODOs and an instructor version
with complete implementations. Avoid giving students a single large finished application at the beginning. Each week's
additions should be motivated by the limitations of the previous implementation.

# 8. Pedagogical principle: teach abstractions in context
Avoid presenting the course as a sequence of definitions to memorise. Build it around engineering problems:

- How can business rules be made independently testable?

- How can independent validation failures be accumulated?

- How can dependent computations be composed?

- How can effects be represented without executing them prematurely?

- How can resources be safely acquired and released?

- How can concurrent work be bounded and cancelled?

- How can large datasets be processed with controlled memory usage?

- How can these properties be verified?

Introduce each abstraction when it provides a useful answer to one of these questions. Use mathematical laws to explain
why it behaves correctly, and practical exercises to demonstrate when it is useful.
Defining course outcome: learners can justify effect boundaries, error semantics, resource lifecycles and
concurrency decisions — not simply reproduce Cats and Cats Effect syntax.
