<a id="readme-top"></a>
[![GitHub Stars][stars-shield]][stars-url]
[![GitHub Issues][issues-shield]][issues-url]
[![Current Version][version-shield]][repo-url]
[![Live Demo][live-demo-shield]][live-demo-url]


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/mt-sinai/audio-to-text">
    <img src="audio-message.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Audio to Text</h3>

  <p align="center">
    Transcribe audio to text
    <br />
    <a href="https://github.com/mt-sinai/audio-to-text"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://att.catuns.xyz">View Demo</a>
    ·
    <a href="https://github.com/mt-sinai/audio-to-text/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    ·
    <a href="https://github.com/mt-sinai/audio-to-text/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">Overview</a>
      <ul>
        <li><a href="#technical-stack">Tech Stack</a></li>
        <li><a href="#core-features">Core Features</a></li>
      </ul>
    </li>
    <li><a href="#setup">Setup</a></li>
    <li><a href="#modules">Modules</a></li>
    <li><a href="#api-reference">API Reference</a></li>
    <li><a href="#database-schema">Database Schema</a></li>
    <li><a href="#implementation-steps">Implementation Steps</a></li>
    <li>
        <a href="#additional-considerations">Additional Considerations</a>
        <ul>
            <li><a href="#bonus-features">Bonus Features</a></li>
            <li><a href="#to-do">To-Dos</a></li>
            <li><a href="#future-features">Future Features</a></li>
        </ul>
    </li>
    <li><a href="#contact">Contact</a></li>

  </ol>
</details>

---

<!-- Overview -->
## Overview

Transcribe audio file into text using the AWS Transcribe API

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

## Technical Stack

### Backend
- **Framework**: Spring Boot for the backend RESTful API.
- **Database**: PostgreSQL for storing user data, resources, and discussions.

### Hosting and Deployment
- **Cloud Provider**: AWS for hosting the application.
- **Containerization**: Docker for containerizing the application for easier deployment and scalability.`

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

<!-- Core Features -->
## Core Features
- **Transcibe**: upload an audio file to transcribe

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

<!-- Setup -->
## Setup

Copy environment variables to the [.env](.env) file

```bash
cp .env.example .env
```

`DATABASE_HOST`
`DATABASE_PORT`
`DATABASE_NAME`
`DATABASE_PASSWORD`
`DATABASE_USER`
`JWT_SECRET`
`AWS_ACCESS_KEY_ID`
`AWS_SECRET_ACCESS_KEY`
`AWS_S3_BUCKET_NAME`

Start the services

```bash
docker compose up -d
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---


<!-- API Reference -->

## [API Reference](http://localhost:8080/swagger-ui.html)

### Audio Service
- `POST /api/audio/upload`
   - Upload an audio file to AWS S3.
- `GET /api/audio/list`
   - Retrieve a list of all uploaded audio files.
- `GET /api/audio/{fileId}`
   - Get details of a specific uploaded audio file.
- `DELETE /api/audio/{fileId}`
   - Remove a specific audio file from S3.
- `POST /api/audio/analyze`
   - Analyze audio file for quality and transcription compatibility.

### Transcribe Service
- `POST /api/transcribe/start`
   - Initiate a transcription job for an uploaded audio file.
- `GET /api/transcribe/{jobId}`
   - Check the status and retrieve results of a transcription job.
- `POST /api/transcribe/batch`
   - Begin batch transcription for multiple audio files.
- `GET /api/transcribe/batch/{batchId}`
   - Check the status of a batch transcription job.

### Transcriptions Service
- `GET /api/transcriptions/list`
   - Retrieve a list of all completed transcriptions.
- `GET /api/transcriptions/{transcriptionId}`
   - Fetch a specific transcription result.
- `DELETE /api/transcriptions/{transcriptionId}`
   - Remove a specific transcription result.

### Statistics Service
- `GET /api/stats`
   - Retrieve usage statistics (e.g., total uploads, completed transcriptions).



<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

<!-- Database Schema -->
## Database Schema

### [Audio Service]()

#### [AudioFile]()
- `id` (Primary key)
- `file_name`
- `file_path` (S3 URI)
- `file_size` (in bytes)
- `duration` (in seconds)
- `format` (e.g., 'mp3', 'wav')
- `upload_date`
- `user_id` (Foreign key to Users table)
- `status` (ENUM: 'uploaded', 'analyzed', 'transcribing', 'completed', 'error')

#### [AudioAnalysis]()
- `id` (Primary key)
- `audio_file_id` (Foreign key to AudioFiles table)
- `sample_rate`
- `bit_depth`
- `channels`
- `quality_score`
- `analysis_date`

### [Transcribe Service]()

#### [TranscriptionJob]()
- `id` (Primary key)
- `audio_file_id` (Foreign key to AudioFiles table)
- `job_name` (unique, as used in AWS Transcribe)
- `status` (ENUM: 'in_progress', 'completed', 'failed')
- `start_time`
- `end_time`
- `language_code`

### [Transcriptions Service]()

#### [Transcription]()
- `id` (Primary key)
- `transcription_job_id` (Foreign key to [TranscriptionJob](#transcriptionjob) table)
- `text_content` (Full transcribed text)
- `confidence_score`
- `speaker_labels` (JSON, if speaker diarization is used)
- `created_at`

#### [TranscriptionSegment]()
- `id` (Primary key)
- `transcription_id` (Foreign key to Transcriptions table)
- `start_time`
- `end_time`
- `text`
- `confidence`
- `speaker_label` (if applicable)

### [Statistics Service]()

#### [UsageStat]()
- `id` (Primary key)
- `user_id` (Foreign key to Users table)
- `date`
- `audio_files_uploaded`
- `total_audio_duration`
- `transcriptions_completed`
- `total_transcription_length`


<p align="right">(<a href="#readme-top">back to top</a>)</p>

---
<!-- Implementation Steps -->
## Implementation Steps

1. **Set Up the Development Environment**
    - [x] Install Java Development Kit (JDK) 17
    - [x] Set up a Spring Boot project using Spring Initializr
    - [x] Configure the project structure and dependencies (Spring Web, Spring Data JPA, Spring Security, AWS SDK)
    - [x] Set up a PostgreSQL database for local development

2. **Configure AWS Services**
    - [x] Create an AWS account if not already available
    - [x] Set up an S3 bucket for audio file and transcript storage
    - [x] Configure IAM roles and policies for S3 and Transcribe access
    - [x] Set up AWS Transcribe service

3. **Implement Core Services**
    - [] Create AudioService for handling audio file operations
    - [] Implement TranscribeService for managing transcription jobs
    - [] Develop TranscriptionsService for storing and retrieving transcriptions
    - [] Set up UserService for user management and authentication

4. **Database Setup**
    - [] Create database schema as per the defined structure
    - [] Implement JPA entities for each table
    - [] Set up repositories for database operations

5. **API Development**
    - [] Implement RESTful endpoints for audio upload and management
    - [] Create endpoints for starting and managing transcription jobs
    - [] Develop API for retrieving and managing transcriptions
    - [] Implement user registration and authentication endpoints

6. **Integration with AWS**
    - [] Implement S3 client for file upload and retrieval
    - [] Integrate AWS Transcribe for audio transcription
    - [] Set up asynchronous job processing for transcription tasks

7. **Security Implementation**
    - [] Configure Spring Security for authentication and authorization
    - [] Implement JWT for secure API access
    - [] Set up CORS configuration

8. **Error Handling and Logging**
    - [] Implement global exception handling
    - [] Set up logging using SLF4J and Logback
    - [] Create custom exceptions for specific error scenarios

9. **Testing**
    - [] Write unit tests for services and repositories
    - [] Develop integration tests for API endpoints
    - [] Perform end-to-end testing of the transcription process

10. **Documentation**
    - [] Generate API documentation using Swagger/OpenAPI
    - [] Create README with project setup and running instructions
    - [] Document AWS configuration steps

11. **Optimization and Scaling**
    - [] Implement caching for frequently accessed data
    - [] Set up connection pooling for database efficiency
    - [] Configure auto-scaling for AWS resources

12. **Monitoring and Analytics**
    - [] Implement health check endpoints
    - [] Set up monitoring using AWS CloudWatch
    - [] Create dashboards for key metrics and usage statistics

13. **Deployment**
    - [] Containerize the application using Docker
    - [] Set up CI/CD pipeline (e.g., using Jenkins or GitHub Actions)
    - [] Deploy to AWS ECS or Kubernetes cluster

14. **User Interface (Optional)**
    - [] Develop a simple front-end interface for file upload and transcription management
    - [] Implement user authentication and dashboard in the UI

15. **Final Testing and Launch**
    - [] Conduct thorough system testing in a staging environment
    - [] Perform security audit and penetration testing
    - [] Launch the application in production environment
<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

<!-- Additional Considerations -->
## Additional Considerations

- **Data Privacy**: Ensure compliance with data protection regulations (e.g., GDPR, CCPA).
    - Implement data encryption at rest and in transit.
    - Provide user options for data deletion and export.

- **Scalability**: Design the architecture to handle increased user load and data volume.
    - Implement horizontal scaling for application servers.
    - Use AWS Auto Scaling groups for dynamic resource allocation.

- **Cost Optimization**:
    - Implement tiered storage solutions (e.g., S3 Intelligent-Tiering).
    - Set up AWS Budgets and Cost Explorer for monitoring expenses.

- **Multi-language Support**:
    - Extend transcription capabilities to multiple languages.
    - Implement language detection for automatic language selection.

- **Accessibility**:
    - Ensure API documentation is screen reader compatible.
    - Provide alternative text for any non-text content in the UI.

- **Performance Optimization**:
    - Implement caching strategies (e.g., Redis) for frequently accessed data.
    - Use AWS CloudFront for content delivery acceleration.

- **Disaster Recovery**:
    - Set up regular backups of database and S3 objects.
    - Implement a multi-region deployment strategy for high availability.

- **API Versioning**:
    - Implement API versioning to support backward compatibility.
    - Provide clear deprecation policies for older API versions.

- **Rate Limiting**:
    - Implement API rate limiting to prevent abuse and ensure fair usage.
    - Set up different rate limits for various user tiers.

- **Monitoring and Alerting**:
    - Set up comprehensive logging and monitoring using AWS CloudWatch.
    - Implement alerting for critical errors and performance issues.

- **Security Enhancements**:
    - Regularly update dependencies to patch security vulnerabilities.
    - Implement Multi-Factor Authentication (MFA) for user accounts.

- **Compliance**:
    - Ensure adherence to industry-specific regulations (e.g., HIPAA for healthcare).
    - Implement audit trails for all data access and modifications.

- **User Experience**:
    - Provide real-time progress updates for long-running transcription jobs.
    - Implement a user-friendly interface for managing audio files and transcriptions.

- **Integration Capabilities**:
    - Develop webhooks for real-time notifications of job completions.
    - Create SDKs or client libraries for easy integration with other systems.

- **Ethical Considerations**:
    - Implement safeguards against misuse of the transcription service.
    - Provide clear terms of service regarding content ownership and usage.


<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

<!-- CONTRIBUTORS -->
## Contributors

<a href="https://github.com/mt-sinai/audio-to-text/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=mt-sinai/audio-to-text" />
</a>

---

<!-- CONTACT -->
## Contact

Devin Catuns - <a href="mailto:devin@catuns.xyz">devin@catuns.xyz</a>

[![LinkedIn][linkedin-shield]][linkedin-url]
[![portfolio][porfolio-shield]][portfolio-url]


<p align="right">(<a href="#readme-top">back to top</a>)</p>

---
## Buy me a coffee

Whether you use this project, have learned something from it, or just like it, please consider supporting it by buying me a coffee, so I can dedicate more time on open-source projects like this :)

<a href="https://www.buymeacoffee.com/devincatunj" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[version-shield]: https://img.shields.io/badge/version-0.0.1-green.svg?style=for-the-badge
[contributors-shield]: https://img.shields.io/github/contributors/mt-sinai/audio-to-text.svg?style=for-the-badge&logo=github
[contributors-url]: https://github.com/mt-sinai/audio-to-text/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/mt-sinai/audio-to-text.svg?style=for-the-badge
[forks-url]: https://github.com/mt-sinai/audio-to-text/network/members
[stars-shield]: https://img.shields.io/github/stars/mt-sinai/audio-to-text.svg?style=for-the-badge&&logo=github
[stars-url]: https://github.com/mt-sinai/audio-to-text/stargazers
[issues-shield]: https://img.shields.io/github/issues/mt-sinai/audio-to-text.svg?style=for-the-badge
[issues-url]: https://github.com/mt-sinai/audio-to-text/issues
[license-shield]: https://img.shields.io/github/license/mt-sinai/audio-to-text.svg?style=for-the-badge
[license-url]: https://github.com/mt-sinai/audio-to-text/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=0A66C2
[linkedin-url]: https://www.linkedin.com/in/devin-catuns/
[live-demo-shield]: https://img.shields.io/badge/demo-offline-red.svg?style=for-the-badge
[live-demo-url]: https://att.catuns.xyz
[repo-url]: https://github.com/mt-sinai/audio-to-text
[porfolio-shield]: https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white
[portfolio-url]: https://catuns.xyz/
